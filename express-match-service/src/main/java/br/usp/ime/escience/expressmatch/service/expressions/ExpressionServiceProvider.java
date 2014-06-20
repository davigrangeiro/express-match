package br.usp.ime.escience.expressmatch.service.expressions;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.usp.ime.escience.expressmatch.exception.ExpressMatchException;
import br.usp.ime.escience.expressmatch.model.Expression;
import br.usp.ime.escience.expressmatch.model.ExpressionType;
import br.usp.ime.escience.expressmatch.model.Point;
import br.usp.ime.escience.expressmatch.model.Stroke;
import br.usp.ime.escience.expressmatch.model.Symbol;
import br.usp.ime.escience.expressmatch.model.User;
import br.usp.ime.escience.expressmatch.model.repository.ExpressionRepository;
import br.usp.ime.escience.expressmatch.model.repository.ExpressionTypeRepository;
import br.usp.ime.escience.expressmatch.model.repository.SymbolRepository;
import br.usp.ime.escience.expressmatch.model.repository.UserInfoRepository;
import br.usp.ime.escience.expressmatch.model.status.ExpressionStatusEnum;

@Service
@Transactional
public class ExpressionServiceProvider {

	private static final int NOT_EVALUATED_STROKE = -1;

	private static final String NOT_EVALUATED_YET = "Not evaluated yet";

	private static final Logger logger = LoggerFactory.getLogger(ExpressionServiceProvider.class);
	
	@Autowired
	private ExpressionRepository expressionRepository;
	
	@Autowired
	private ExpressionTypeRepository expressionTypeRepository;
	
	@Autowired
	private SymbolRepository symbolRepository;
	
	@Autowired
	private UserInfoRepository userInfoRepository;
	
	public void saveExpressions(List<Expression> expressions){
		logger.info("Saving expressions.");
		this.expressionRepository.save(expressions);
		logger.info(MessageFormat.format("Saved {0} expressions.", expressions.size()));
	}
	
	public List<ExpressionType> generateExpressionTypesByExpression(List<Expression> expressions){
		logger.info("Generating expression Types");
		List<ExpressionType> types = new ArrayList<ExpressionType>(); 
		for (Expression expression : expressions) {
			ExpressionType type = expression.getExpressionType();
			type.setLabel(expression.getLabel());
			expression.setExpressionType(type);
			types.add(type);
		}
		this.expressionTypeRepository.save(types);
		logger.info(MessageFormat.format("Flushing expression Types, Inserted {0} records.", types.size()));
		this.expressionRepository.flush();
		return types;
	}
	
	public void saveExpressionTypes(List<ExpressionType> expressions){
		logger.info("Saving expression types.");
		this.expressionTypeRepository.save(expressions);
		logger.info(MessageFormat.format("Saved {0} expression types.", expressions.size()));
	}
	
	public List<ExpressionType> loadExpressionTypes(){
		List<ExpressionType> res = null;
		logger.info("Retrieving expression types.");
		res = this.expressionTypeRepository.findAll();
		logger.info(MessageFormat.format("Retrieved {0} expression types.", res.size()));
		return res;
	}
	
	public Expression loadExpressionForExpressionType(ExpressionType type){
		Expression res = this.expressionRepository.getExpressionByExpressionType(type.getId());
		fullLoadExpression(res);
		return res;
	}

	private void fullLoadExpression(Expression res) {
		if(null != res && null != res.getSymbols()){
			for (Symbol s : res.getSymbols()) {
				if(null != s.getStrokes()){
					for (Stroke str : s.getStrokes()) {
						List<Point> points = new ArrayList<Point>(str.getPoints());
						str.setPoints(points);
					}
				}
			}
		}
	}
	
	public Expression loadExpressionUserForExpressionType(ExpressionType type, User user){
		Expression res =  this.expressionRepository.getExpressionByUserAndType(type.getId(), user.getNick());
		
		if (null != res){
			fullLoadExpression(res);
		}
		
		return res;
	}
	
	public List<Expression> findTranscribedExpressions(){
		return this.expressionRepository.findByExpressionStatus(ExpressionStatusEnum.EXPRESSION_TRANSCRIBED.getValue());
	}
	
	public List<Expression> findEvaluatedExpressions(){
		return this.expressionRepository.findByExpressionStatus(ExpressionStatusEnum.EXPRESSION_EVALUATED.getValue());
	}
	
	public List<Expression> findValidatedExpressions(){
		return this.expressionRepository.findByExpressionStatus(ExpressionStatusEnum.EXPRESSION_VALIDATED.getValue());
	}

	public void saveTranscription(Stroke[] strokes, ExpressionType expressionType, User currentUser, Expression userExpression) throws ExpressMatchException {
		try {
			
			Expression e = null;
			if (null != userExpression){
				e = userExpression;
			} else {
				e = new Expression();
			}
			cleanExpressionData(e);
			
			e.setExpressionType(this.expressionTypeRepository.findOne(expressionType.getId()));
			e.setLabel(NOT_EVALUATED_YET);
			e.setUserInfo(this.userInfoRepository.getUserInfoByUserNick(currentUser.getNick()));
			e.setSymbols(new ArrayList<Symbol>());
			
			Symbol s = new Symbol();
			s.setHref(NOT_EVALUATED_YET);
			s.setExpression(e);
			s.setLabel(NOT_EVALUATED_YET);
			s.setStrokes(new ArrayList<Stroke>());
			
			for (int i = 0; i < strokes.length; i++) {
				
				Stroke stroke = new Stroke();
				stroke.setStrokeId(NOT_EVALUATED_STROKE);
				stroke.setSymbol(s);
				stroke.setPoints(new ArrayList<Point>());
				
				for (Point point : strokes[i].getPoints()) {
					Point p = new Point();
					p.setX(point.getX());
					p.setY(point.getY());
					p.setTime(point.getTime());
					p.setStroke(stroke);
					stroke.getPoints().add(p);
				}
				
				s.getStrokes().add(stroke);
			}
			
			e.getSymbols().add(s);
			
			this.expressionRepository.save(e);
			
			logger.info(MessageFormat.format("Saved expression with id {0}", e.getId()));
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			throw new ExpressMatchException(MessageFormat.format("There was an error while saving the transcription of expression type {0}.", expressionType.getId()));
		}
	}

	private void cleanExpressionData(Expression expression) {
		for (Symbol s: expression.getSymbols()) {
			symbolRepository.delete(s.getId());
		}
	}
	
	public List<Stroke> normalizeTranscriptionBySymbolModel(List<Stroke> transcription, Symbol symbolModel) {
		List<Stroke> normalizedStrokes = new ArrayList<>();
		Symbol currentTranscription = new Symbol(transcription);

		Point modelBoundingBoxSize = symbolModel.getBoundingBoxSize(),
			  transcriptionBoundingBoxSize = currentTranscription.getBoundingBoxSize();
		
		double xScale = transcriptionBoundingBoxSize.getX() / modelBoundingBoxSize.getX();
		double yScale = transcriptionBoundingBoxSize.getY() / modelBoundingBoxSize.getY(); 
		
		for (Stroke stroke : transcription) {
			Stroke normalized = new Stroke();
			
			for (Point p : stroke.getPoints()) {
				Point newPoint = new Point((float)(p.getX() * xScale),(float)(p.getY() * yScale));
				normalized.addCheckingBoundingBox(newPoint);
			}
			
			normalizedStrokes.add(normalized);
		}
		
		return normalizedStrokes;
	}

	
}
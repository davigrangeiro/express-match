package br.usp.ime.escience.expressmatch.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.usp.ime.escience.expressmatch.model.Expression;

public interface ExpressionRepository extends JpaRepository<Expression, Integer>{

	
	@Query("select e from Expression e where e.expressionType.id = ?1 and e.userInfo.user.nick like ?2")
	public Expression getExpressionByUserAndType(Integer type, String nick);
	
	@Query("select e.expression from ExpressionType e where e.id = ?1")
	public Expression getExpressionByExpressionType(Integer type);
	
	public List<Expression> findByExpressionStatus(Integer expressionStatus);
	
}

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.annotation.Documented;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface Tabela {
    /**
     * Nome da tabela no banco de dados
     */
    String value();
    
    /**
     * Nome alternativo da tabela (caso value() não seja suficiente)
     */
    String nome() default "";
    
    /**
     * Schema do banco de dados onde a tabela está localizada
     */
    String schema() default "";
    
    /**
     * Catálogo do banco de dados
     */
    String catalogo() default "";
    
    /**
     * Define se a tabela é somente leitura
     */
    boolean somenteLeitura() default false;
    
    /**
     * Índices da tabela
     */
    String[] indices() default {};
    
    /**
     * Comentário/descrição da tabela
     */
    String descricao() default "";
}

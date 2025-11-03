public class TesteAnnotation {
    public static void main(String[] args) {
        // Lê a annotation em tempo de execução
        Class<Usuario> classe = Usuario.class;
        
        // Verifica se a classe possui a annotation @Tabela
        if (classe.isAnnotationPresent(Tabela.class)) {
            // Obtém a annotation
            Tabela tabela = classe.getAnnotation(Tabela.class);
            
            // Imprime o nome da tabela
            System.out.println("Nome da tabela: " + tabela.value());
            
            // Imprime outras informações se disponíveis
            if (!tabela.schema().isEmpty()) {
                System.out.println("Schema: " + tabela.schema());
            }
            
            if (!tabela.catalogo().isEmpty()) {
                System.out.println("Catálogo: " + tabela.catalogo());
            }
            
            System.out.println("Somente leitura: " + tabela.somenteLeitura());
            
            if (tabela.indices().length > 0) {
                System.out.println("Índices: " + String.join(", ", tabela.indices()));
            }
            
            if (!tabela.descricao().isEmpty()) {
                System.out.println("Descrição: " + tabela.descricao());
            }
            
            // Monta o nome completo
            String nomeCompleto = tabela.value();
            if (!tabela.schema().isEmpty()) {
                nomeCompleto = tabela.schema() + "." + nomeCompleto;
            }
            if (!tabela.catalogo().isEmpty()) {
                nomeCompleto = tabela.catalogo() + "." + nomeCompleto;
            }
            
            System.out.println("\nNome completo da tabela: " + nomeCompleto);
            
        } else {
            System.out.println("A classe " + classe.getSimpleName() + " não possui a annotation @Tabela");
        }
    }
}

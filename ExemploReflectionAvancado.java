import java.util.List;
import java.util.Map;

public class ExemploReflectionAvancado {
    
    public static void main(String[] args) {
        System.out.println("=== EXEMPLOS AVANÇADOS DE REFLECTION ===\n");
        
        exemploManipulacaoObjetos();
        System.out.println("\n" + "=".repeat(50) + "\n");
        
        exemploGeracaoSQL();
        System.out.println("\n" + "=".repeat(50) + "\n");
        
        exemploAnaliseClasse();
    }
    
    /**
     * Exemplo 1: Manipulação dinâmica de objetos
     */
    private static void exemploManipulacaoObjetos() {
        System.out.println("EXEMPLO 1: Manipulação Dinâmica de Objetos");
        System.out.println("-".repeat(50));
        
        try {
            // Criar múltiplos usuários usando reflection
            Usuario[] usuarios = new Usuario[3];
            
            String[][] dados = {
                {"1", "Ana Costa", "ana@email.com", "111.222.333-44"},
                {"2", "Carlos Souza", "carlos@email.com", "222.333.444-55"},
                {"3", "Beatriz Lima", "beatriz@email.com", "333.444.555-66"}
            };
            
            for (int i = 0; i < dados.length; i++) {
                usuarios[i] = TabelaReflection.criarInstancia(Usuario.class);
                TabelaReflection.definirValorCampo(usuarios[i], "id", Long.parseLong(dados[i][0]));
                TabelaReflection.definirValorCampo(usuarios[i], "nome", dados[i][1]);
                TabelaReflection.definirValorCampo(usuarios[i], "email", dados[i][2]);
                TabelaReflection.definirValorCampo(usuarios[i], "cpf", dados[i][3]);
            }
            
            System.out.println("✓ " + usuarios.length + " usuários criados dinamicamente\n");
            
            // Listar todos os usuários
            for (Usuario user : usuarios) {
                Map<String, Object> dados_user = TabelaReflection.objetoParaMap(user);
                System.out.println("Usuário ID " + dados_user.get("id") + ": " + 
                                 dados_user.get("nome") + " (" + dados_user.get("email") + ")");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Exemplo 2: Geração automática de SQL
     */
    private static void exemploGeracaoSQL() {
        System.out.println("EXEMPLO 2: Geração Automática de SQL");
        System.out.println("-".repeat(50));
        
        System.out.println("Tabela: " + TabelaReflection.obterNomeCompletoTabela(Usuario.class));
        System.out.println();
        
        System.out.println("Query SELECT:");
        System.out.println("  " + TabelaReflection.gerarQuerySelect(Usuario.class));
        System.out.println();
        
        System.out.println("Query INSERT:");
        System.out.println("  " + TabelaReflection.gerarQueryInsert(Usuario.class));
        System.out.println();
        
        System.out.println("Query UPDATE:");
        System.out.println("  " + TabelaReflection.gerarQueryUpdate(Usuario.class, "id"));
        System.out.println();
        
        System.out.println("Query DELETE:");
        System.out.println("  " + TabelaReflection.gerarQueryDelete(Usuario.class, "id"));
    }
    
    /**
     * Exemplo 3: Análise de estrutura de classe
     */
    private static void exemploAnaliseClasse() {
        System.out.println("EXEMPLO 3: Análise de Estrutura de Classe");
        System.out.println("-".repeat(50));
        
        // Verificar se possui annotation
        boolean temAnnotation = TabelaReflection.possuiAnnotationTabela(Usuario.class);
        System.out.println("Possui @Tabela: " + (temAnnotation ? "SIM ✓" : "NÃO ✗"));
        
        if (temAnnotation) {
            // Obter informações da tabela
            TabelaInfo info = TabelaReflection.obterInfoTabela(Usuario.class);
            System.out.println("\nInformações da Tabela:");
            System.out.println("  Nome: " + info.getNomeTabela());
            System.out.println("  Schema: " + info.getSchema());
            System.out.println("  Nome Completo: " + info.getNomeCompleto());
            System.out.println("  Somente Leitura: " + info.isSomenteLeitura());
            System.out.println("  Índices: " + String.join(", ", info.getIndices()));
            System.out.println("  Descrição: " + info.getDescricao());
        }
        
        // Listar campos
        System.out.println("\nCampos da Classe:");
        List<String> campos = TabelaReflection.obterNomesCampos(Usuario.class);
        for (int i = 0; i < campos.size(); i++) {
            System.out.println("  " + (i + 1) + ". " + campos.get(i));
        }
        
        // Contar métodos públicos
        int qtdMetodos = TabelaReflection.listarMetodosPublicos(Usuario.class).size();
        System.out.println("\nTotal de Métodos Públicos: " + qtdMetodos);
    }
}

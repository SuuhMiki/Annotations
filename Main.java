import java.util.Map;

public class Main {
    public static void main(String[] args) {
        try {
            System.out.println("========================================");
            System.out.println("DEMONSTRAÇÃO DE REFLECTION COM @TABELA");
            System.out.println("========================================\n");
            
            // 1. Exibir informações completas da classe
            TabelaReflection.exibirInformacoesCompletas(Usuario.class);
            
            System.out.println("\n========================================");
            System.out.println("MANIPULAÇÃO DE OBJETOS COM REFLECTION");
            System.out.println("========================================\n");
            
            // 2. Criar uma instância usando reflection
            Usuario usuario = TabelaReflection.criarInstancia(Usuario.class);
            System.out.println("✓ Instância criada usando reflection");
            
            // 3. Definir valores usando reflection
            TabelaReflection.definirValorCampo(usuario, "id", 1L);
            TabelaReflection.definirValorCampo(usuario, "nome", "João Silva");
            TabelaReflection.definirValorCampo(usuario, "email", "joao@email.com");
            TabelaReflection.definirValorCampo(usuario, "cpf", "123.456.789-00");
            System.out.println("✓ Valores definidos usando reflection");
            
            // 4. Obter valores usando reflection
            System.out.println("\n[Valores dos Campos via Reflection]");
            for (String nomeCampo : TabelaReflection.obterNomesCampos(Usuario.class)) {
                Object valor = TabelaReflection.obterValorCampo(usuario, nomeCampo);
                System.out.println("  " + nomeCampo + " = " + valor);
            }
            
            // 5. Converter objeto para Map
            System.out.println("\n[Objeto Convertido para Map]");
            Map<String, Object> mapaUsuario = TabelaReflection.objetoParaMap(usuario);
            mapaUsuario.forEach((campo, valor) -> 
                System.out.println("  " + campo + " -> " + valor)
            );
            
            // 6. Criar outro objeto e preencher com Map
            System.out.println("\n[Criando Novo Objeto a partir de Map]");
            Usuario usuario2 = TabelaReflection.criarInstancia(Usuario.class);
            Map<String, Object> novosValores = Map.of(
                "id", 2L,
                "nome", "Maria Santos",
                "email", "maria@email.com",
                "cpf", "987.654.321-00"
            );
            TabelaReflection.preencherObjeto(usuario2, novosValores);
            System.out.println("✓ Objeto preenchido: " + usuario2.getNome() + " - " + usuario2.getEmail());
            
            // 7. Invocar métodos usando reflection
            System.out.println("\n[Invocando Métodos via Reflection]");
            Object nomeRetornado = TabelaReflection.invocarMetodo(usuario2, "getNome");
            System.out.println("  getNome() retornou: " + nomeRetornado);
            
            System.out.println("\n========================================");
            System.out.println("✓ DEMONSTRAÇÃO CONCLUÍDA COM SUCESSO!");
            System.out.println("========================================");
            
        } catch (Exception e) {
            System.err.println("Erro durante execução: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

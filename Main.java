public class Main {
    public static void main(String[] args) {
        // Extrair informações da annotation da classe Usuario
        TabelaInfo info = TabelaInfo.extrairDaClasse(Usuario.class);
        
        System.out.println("=== Informações da Tabela ===");
        System.out.println("Nome da tabela: " + info.getNomeTabela());
        System.out.println("Schema: " + info.getSchema());
        System.out.println("Nome completo: " + info.getNomeCompleto());
        System.out.println("Somente leitura: " + info.isSomenteLeitura());
        System.out.println("Índices: " + java.util.Arrays.toString(info.getIndices()));
        System.out.println("Descrição: " + info.getDescricao());
        System.out.println();
        System.out.println("ToString completo:");
        System.out.println(info);
    }
}

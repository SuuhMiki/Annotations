public class TabelaInfo {
    private String nomeTabela;
    private String schema;
    private String catalogo;
    private boolean somenteLeitura;
    private String[] indices;
    private String descricao;

    public TabelaInfo(String nomeTabela, String schema, String catalogo, 
                      boolean somenteLeitura, String[] indices, String descricao) {
        this.nomeTabela = nomeTabela;
        this.schema = schema;
        this.catalogo = catalogo;
        this.somenteLeitura = somenteLeitura;
        this.indices = indices;
        this.descricao = descricao;
    }

    // Método estático para extrair informações da annotation
    public static TabelaInfo extrairDaClasse(Class<?> classe) {
        if (!classe.isAnnotationPresent(Tabela.class)) {
            throw new IllegalArgumentException(
                "A classe " + classe.getName() + " não possui a annotation @Tabela"
            );
        }

        Tabela tabela = classe.getAnnotation(Tabela.class);
        
        // Usa o 'nome' se definido, senão usa 'value'
        String nomeTabela = !tabela.nome().isEmpty() ? tabela.nome() : tabela.value();
        
        return new TabelaInfo(
            nomeTabela,
            tabela.schema(),
            tabela.catalogo(),
            tabela.somenteLeitura(),
            tabela.indices(),
            tabela.descricao()
        );
    }

    // Getters
    public String getNomeTabela() {
        return nomeTabela;
    }

    public String getSchema() {
        return schema;
    }

    public String getCatalogo() {
        return catalogo;
    }

    public boolean isSomenteLeitura() {
        return somenteLeitura;
    }

    public String[] getIndices() {
        return indices;
    }

    public String getDescricao() {
        return descricao;
    }

    // Método para obter o nome completo da tabela (com schema se existir)
    public String getNomeCompleto() {
        StringBuilder nomeCompleto = new StringBuilder();
        
        if (!catalogo.isEmpty()) {
            nomeCompleto.append(catalogo).append(".");
        }
        
        if (!schema.isEmpty()) {
            nomeCompleto.append(schema).append(".");
        }
        
        nomeCompleto.append(nomeTabela);
        
        return nomeCompleto.toString();
    }

    @Override
    public String toString() {
        return "TabelaInfo{" +
                "nomeTabela='" + nomeTabela + '\'' +
                ", schema='" + schema + '\'' +
                ", catalogo='" + catalogo + '\'' +
                ", somenteLeitura=" + somenteLeitura +
                ", indices=" + java.util.Arrays.toString(indices) +
                ", descricao='" + descricao + '\'' +
                ", nomeCompleto='" + getNomeCompleto() + '\'' +
                '}';
    }
}

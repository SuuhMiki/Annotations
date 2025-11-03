import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TabelaReflection {
    
    /**
     * Extrai todas as informações da annotation @Tabela de uma classe
     */
    public static TabelaInfo obterInfoTabela(Class<?> classe) {
        return TabelaInfo.extrairDaClasse(classe);
    }
    
    /**
     * Verifica se uma classe possui a annotation @Tabela
     */
    public static boolean possuiAnnotationTabela(Class<?> classe) {
        return classe.isAnnotationPresent(Tabela.class);
    }
    
    /**
     * Obtém o nome completo da tabela (schema.tabela)
     */
    public static String obterNomeCompletoTabela(Class<?> classe) {
        if (!possuiAnnotationTabela(classe)) {
            return null;
        }
        TabelaInfo info = obterInfoTabela(classe);
        return info.getNomeCompleto();
    }
    
    /**
     * Lista todos os campos (fields) de uma classe
     */
    public static List<Field> listarCampos(Class<?> classe) {
        List<Field> campos = new ArrayList<>();
        Field[] todosOsCampos = classe.getDeclaredFields();
        
        for (Field campo : todosOsCampos) {
            campos.add(campo);
        }
        
        return campos;
    }
    
    /**
     * Obtém os nomes de todos os campos de uma classe
     */
    public static List<String> obterNomesCampos(Class<?> classe) {
        List<String> nomes = new ArrayList<>();
        
        for (Field campo : listarCampos(classe)) {
            nomes.add(campo.getName());
        }
        
        return nomes;
    }
    
    /**
     * Obtém o valor de um campo específico de um objeto usando reflection
     */
    public static Object obterValorCampo(Object objeto, String nomeCampo) 
            throws NoSuchFieldException, IllegalAccessException {
        Field campo = objeto.getClass().getDeclaredField(nomeCampo);
        campo.setAccessible(true);
        return campo.get(objeto);
    }
    
    /**
     * Define o valor de um campo específico de um objeto usando reflection
     */
    public static void definirValorCampo(Object objeto, String nomeCampo, Object valor) 
            throws NoSuchFieldException, IllegalAccessException {
        Field campo = objeto.getClass().getDeclaredField(nomeCampo);
        campo.setAccessible(true);
        campo.set(objeto, valor);
    }
    
    /**
     * Converte um objeto em um Map (campo -> valor)
     */
    public static Map<String, Object> objetoParaMap(Object objeto) {
        Map<String, Object> mapa = new HashMap<>();
        Class<?> classe = objeto.getClass();
        
        for (Field campo : classe.getDeclaredFields()) {
            try {
                campo.setAccessible(true);
                Object valor = campo.get(objeto);
                mapa.put(campo.getName(), valor);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        
        return mapa;
    }
    
    /**
     * Cria uma instância de uma classe usando reflection
     */
    public static <T> T criarInstancia(Class<T> classe) 
            throws ReflectiveOperationException {
        return classe.getDeclaredConstructor().newInstance();
    }
    
    /**
     * Preenche um objeto com valores de um Map usando reflection
     */
    public static void preencherObjeto(Object objeto, Map<String, Object> valores) {
        Class<?> classe = objeto.getClass();
        
        for (Map.Entry<String, Object> entry : valores.entrySet()) {
            try {
                Field campo = classe.getDeclaredField(entry.getKey());
                campo.setAccessible(true);
                campo.set(objeto, entry.getValue());
            } catch (NoSuchFieldException | IllegalAccessException e) {
                System.err.println("Erro ao definir campo " + entry.getKey() + ": " + e.getMessage());
            }
        }
    }
    
    /**
     * Gera uma query SQL SELECT básica baseada na annotation @Tabela
     */
    public static String gerarQuerySelect(Class<?> classe) {
        if (!possuiAnnotationTabela(classe)) {
            throw new IllegalArgumentException("Classe não possui annotation @Tabela");
        }
        
        TabelaInfo info = obterInfoTabela(classe);
        List<String> campos = obterNomesCampos(classe);
        
        StringBuilder query = new StringBuilder("SELECT ");
        query.append(String.join(", ", campos));
        query.append(" FROM ");
        query.append(info.getNomeCompleto());
        
        return query.toString();
    }
    
    /**
     * Gera uma query SQL INSERT básica baseada na annotation @Tabela
     */
    public static String gerarQueryInsert(Class<?> classe) {
        if (!possuiAnnotationTabela(classe)) {
            throw new IllegalArgumentException("Classe não possui annotation @Tabela");
        }
        
        TabelaInfo info = obterInfoTabela(classe);
        List<String> campos = obterNomesCampos(classe);
        
        StringBuilder query = new StringBuilder("INSERT INTO ");
        query.append(info.getNomeCompleto());
        query.append(" (");
        query.append(String.join(", ", campos));
        query.append(") VALUES (");
        
        List<String> placeholders = new ArrayList<>();
        for (int i = 0; i < campos.size(); i++) {
            placeholders.add("?");
        }
        query.append(String.join(", ", placeholders));
        query.append(")");
        
        return query.toString();
    }
    
    /**
     * Gera uma query SQL UPDATE básica baseada na annotation @Tabela
     */
    public static String gerarQueryUpdate(Class<?> classe, String campoChave) {
        if (!possuiAnnotationTabela(classe)) {
            throw new IllegalArgumentException("Classe não possui annotation @Tabela");
        }
        
        TabelaInfo info = obterInfoTabela(classe);
        List<String> campos = obterNomesCampos(classe);
        
        StringBuilder query = new StringBuilder("UPDATE ");
        query.append(info.getNomeCompleto());
        query.append(" SET ");
        
        List<String> sets = new ArrayList<>();
        for (String campo : campos) {
            if (!campo.equals(campoChave)) {
                sets.add(campo + " = ?");
            }
        }
        query.append(String.join(", ", sets));
        query.append(" WHERE ");
        query.append(campoChave);
        query.append(" = ?");
        
        return query.toString();
    }
    
    /**
     * Gera uma query SQL DELETE básica baseada na annotation @Tabela
     */
    public static String gerarQueryDelete(Class<?> classe, String campoChave) {
        if (!possuiAnnotationTabela(classe)) {
            throw new IllegalArgumentException("Classe não possui annotation @Tabela");
        }
        
        TabelaInfo info = obterInfoTabela(classe);
        
        StringBuilder query = new StringBuilder("DELETE FROM ");
        query.append(info.getNomeCompleto());
        query.append(" WHERE ");
        query.append(campoChave);
        query.append(" = ?");
        
        return query.toString();
    }
    
    /**
     * Lista todos os métodos públicos de uma classe
     */
    public static List<Method> listarMetodosPublicos(Class<?> classe) {
        List<Method> metodos = new ArrayList<>();
        
        for (Method metodo : classe.getDeclaredMethods()) {
            if (Modifier.isPublic(metodo.getModifiers())) {
                metodos.add(metodo);
            }
        }
        
        return metodos;
    }
    
    /**
     * Invoca um método usando reflection
     */
    public static Object invocarMetodo(Object objeto, String nomeMetodo, Object... parametros) 
            throws ReflectiveOperationException {
        Class<?>[] tiposParametros = new Class<?>[parametros.length];
        
        for (int i = 0; i < parametros.length; i++) {
            tiposParametros[i] = parametros[i].getClass();
        }
        
        Method metodo = objeto.getClass().getDeclaredMethod(nomeMetodo, tiposParametros);
        metodo.setAccessible(true);
        
        return metodo.invoke(objeto, parametros);
    }
    
    /**
     * Exibe informações completas sobre uma classe com @Tabela
     */
    public static void exibirInformacoesCompletas(Class<?> classe) {
        System.out.println("=== INFORMAÇÕES DA CLASSE " + classe.getSimpleName() + " ===");
        
        if (possuiAnnotationTabela(classe)) {
            TabelaInfo info = obterInfoTabela(classe);
            System.out.println("\n[Annotation @Tabela]");
            System.out.println(info);
        } else {
            System.out.println("\nClasse não possui annotation @Tabela");
            return;
        }
        
        System.out.println("\n[Campos]");
        for (Field campo : listarCampos(classe)) {
            System.out.println("  - " + campo.getType().getSimpleName() + " " + campo.getName());
        }
        
        System.out.println("\n[Queries SQL Geradas]");
        System.out.println("SELECT: " + gerarQuerySelect(classe));
        System.out.println("INSERT: " + gerarQueryInsert(classe));
        System.out.println("UPDATE: " + gerarQueryUpdate(classe, "id"));
        System.out.println("DELETE: " + gerarQueryDelete(classe, "id"));
        
        System.out.println("\n[Métodos Públicos]");
        for (Method metodo : listarMetodosPublicos(classe)) {
            System.out.println("  - " + metodo.getName() + "()");
        }
    }
}

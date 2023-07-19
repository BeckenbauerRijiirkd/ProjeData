import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Comparator;

public class Funcionario extends Pessoa  {

    private BigDecimal salario;
    private String funcao;

    public Funcionario() {

    }

    public Funcionario(String nome, LocalDate dtNascimento, BigDecimal salario, String funcao) {
        super(nome, dtNascimento);
        this.salario = salario;
        this.funcao = funcao;
    }

    public BigDecimal getSalario() {
        return salario;
    }

    public void setSalario(BigDecimal salario) {
        this.salario = salario;
    }

    public String getFuncao() {
        return funcao;
    }

    public void setFuncao(String funcao) {
        this.funcao = funcao;
    }
    
    public static class NomeComparator implements Comparator<Funcionario> {

        @Override
        public int compare(Funcionario pessoa1, Funcionario pessoa2) {
            String nomePessoa1 = pessoa1.getNome();
            String nomePessoa2 = pessoa2.getNome();
            return nomePessoa1.compareTo(nomePessoa2);
        }

    }
}
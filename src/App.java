import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class App {
    public static void main(String[] args) throws Exception {

        List<Funcionario> funcionarios = new ArrayList<Funcionario>();
        
        funcionarios.add(new Funcionario("Maria", LocalDate.of(2000,10,18), BigDecimal.valueOf(1009.44), "Operador"));
        funcionarios.add(new Funcionario("João", LocalDate.of(1990,05,12), BigDecimal.valueOf(2284.38), "Operador"));
        funcionarios.add(new Funcionario("Caio", LocalDate.of(1961,05,02), BigDecimal.valueOf(9836.14), "Coordenador"));
        funcionarios.add(new Funcionario("Miguel", LocalDate.of(1988,10,14), BigDecimal.valueOf(19119.88), "Diretor"));
        funcionarios.add(new Funcionario("Alice", LocalDate.of(1995,01,05), BigDecimal.valueOf(2234.68), "Recepcionista"));
        funcionarios.add(new Funcionario("Heitor", LocalDate.of(1999,11,19), BigDecimal.valueOf(1582.72), "Operador"));
        funcionarios.add(new Funcionario("Arthur", LocalDate.of(1993,03,31), BigDecimal.valueOf(4071.84), "Contador"));
        funcionarios.add(new Funcionario("Laura", LocalDate.of(1994,07,8), BigDecimal.valueOf(3017.45), "Gerente"));
        funcionarios.add(new Funcionario("Heloisa", LocalDate.of(2003,05,24), BigDecimal.valueOf(1606.85), "Eletricista"));
        funcionarios.add(new Funcionario("Helena", LocalDate.of(1996,9,02), BigDecimal.valueOf(2799.93), "Gerente"));
        
        System.out.println("------------------------Lista Inicial-----------------------");
        ImprimirLista(funcionarios);

        //3.2 Removendo funcionario Joao
        funcionarios.removeIf(funcionario -> funcionario.getNome().equals("João"));

        //3.3 Imprimir todos os funcionários com todas suas informações, sendo que: X
        //• informação de data deve ser exibido no formato dd/mm/aaaa;
        //• informação de valor numérico deve ser exibida no formatado com separador de milhar como ponto e decimal como vírgula
        System.out.println("------------------------Removendo funcionario Joao-----------------------");
        ImprimirLista(funcionarios);

        //3.4 – Os funcionários receberam 10% de aumento de salário, atualizar a lista de funcionários com novo valor.
        aumentoSalarial(funcionarios);
        System.out.println("------------------------Lista Autmento 10%-----------------------");
        ImprimirLista(funcionarios);

        //3.5 – Agrupar os funcionários por função em um MAP, sendo a chave a “função” e o valor a “lista de funcionários”.
        //3.6 – Imprimir os funcionários, agrupados por função.
        Map<String, List<Funcionario>> funcionariosPorFuncao = funcionarios.stream()
        .collect(Collectors.groupingBy(Funcionario::getFuncao));

        funcionariosPorFuncao(funcionariosPorFuncao);

        //3.8 Imprimir os funcionários que fazem aniversário no mês 10 e 12.
        funcionariosPorMesAniversario(funcionarios);

        //3.9 – Imprimir o funcionário com a maior idade, exibir os atributos: nome e idade.
        funcionariosMaisVelho(funcionarios);

        // 3.10 – Imprimir a lista de funcionários por ordem alfabética.
        funcionarios.sort(new Funcionario.NomeComparator());
        System.out.println();
        System.out.println("------------------------Lista em Ordem Alfabetica-----------------------");
        ImprimirLista(funcionarios);

        // 3.11 – Imprimir o total dos salários dos funcionários.
        totalSalarios(funcionarios);

        //3.12 – Imprimir quantos salários mínimos ganha cada funcionário, considerando que o salário mínimo é R$1212.00.
        funcionariosPorSalarioMinimo(funcionarios);

    }

    private static void funcionariosPorSalarioMinimo(List<Funcionario> funcionarios) {

        BigDecimal salarioMin = new BigDecimal(1212.00);
        
        System.out.println();
        System.out.println("------------------------Lista Salarios Minimos-----------------------");

        for (Funcionario funcionario : funcionarios) {
            
            System.out.println(funcionario.getNome() + " Recebe: "+ funcionario.getSalario().divide(salarioMin,MathContext.DECIMAL128).setScale(2, RoundingMode.HALF_EVEN)
            + " Salario(s) minimo(s)");

        }
       
    }


    private static void totalSalarios(List<Funcionario> funcionarios) {
        BigDecimal totalSalarios = new BigDecimal(0);
        
        for (Funcionario funcionario : funcionarios) {
        
            totalSalarios = totalSalarios.add(funcionario.getSalario());
            
        }
        NumberFormat formatter = NumberFormat.getNumberInstance(Locale.getDefault());
        formatter.setMinimumFractionDigits(2);

        System.out.println();
        System.out.println("------------------------Total de Salarios-----------------------");
        System.out.println("Total de Salarios: " + formatter.format(totalSalarios));
    }




    private static void funcionariosMaisVelho(List<Funcionario> funcionarios) {

        LocalDate dataMenor = LocalDate.now();
        String funcionarioMaisVelho = "";

        for (Funcionario funcionario : funcionarios) {

            if (funcionario.getDtNascimento().isBefore(dataMenor)) {
                dataMenor = funcionario.getDtNascimento();
                funcionarioMaisVelho = funcionario.getNome();
            }
        }

        final Period periodo = Period.between(dataMenor, LocalDate.now());
        System.out.println();
        System.out.println("Funcionario mais velho");
        System.out.println("Funcionario: "+ funcionarioMaisVelho +" - Idade: "+ periodo.getYears());
    }

    private static void funcionariosPorMesAniversario(List<Funcionario> funcionarios) {
        List<Funcionario> funcionariosAniversario = new ArrayList<>();
        funcionarios.forEach(funcionario -> {
            LocalDate dataNascimento = funcionario.getDtNascimento();
            int mesNascimento = dataNascimento.getMonthValue();

            if (mesNascimento == 10 || mesNascimento == 12) {
                funcionariosAniversario.add(funcionario);
            }

        });
        System.out.println("Funcionarios que fazm Aniversario no mes 10 ou 12");
        ImprimirLista(funcionariosAniversario);
    }



    private static void funcionariosPorFuncao(Map<String, List<Funcionario>> funcionariosPorFuncao) {
        
        Set<String> funcoes = funcionariosPorFuncao.keySet();

        System.out.println("Funcionarios por Função");
        System.out.println("------------------------------------------------------------");

        for (String funcao : funcoes) {
            System.out.println(funcao+": ");
            List<Funcionario> funcionariosDaFuncao = funcionariosPorFuncao.get(funcao);
            ImprimirLista(funcionariosDaFuncao);
            System.out.println();
        }
       
    }

    private static void aumentoSalarial(List<Funcionario> funcionarios) {
        funcionarios.stream()
        .forEach(funcionario -> {
            BigDecimal salarioAtual = funcionario.getSalario();
            BigDecimal aumento = salarioAtual.multiply(BigDecimal.valueOf(0.1)); 
            BigDecimal novoSalario = salarioAtual.add(aumento); 
            funcionario.setSalario(novoSalario);
        });
    }

    private static void ImprimirLista(List<Funcionario> funcionarios) {
        System.out.println("------------------------------------------------------------");
        System.out.printf("|%10s |%15s |%10s |%15s |", "Nome", " Data Nascimento", "Salário", "Função");
        System.out.println();
        System.out.println("------------------------------------------------------------");

        NumberFormat formatter = NumberFormat.getNumberInstance(Locale.getDefault());
        formatter.setMinimumFractionDigits(2); 

        for (Funcionario funcionario : funcionarios) {
            System.out.format("|%10s |%16s |%10s |%16s| ",
            funcionario.getNome(), funcionario.getDtNascimento().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), formatter.format(funcionario.getSalario()), funcionario.getFuncao());
            System.out.println();
        }
        System.out.println("-----------------------------------------------------------");
    }
}

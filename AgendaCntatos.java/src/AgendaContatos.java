//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import java.io.*;
import java.util.*;

// Representa um contato (POJO)
class Contato {
    private String nome;
    private String telefone;

    public Contato(String nome, String telefone) {
        this.nome = nome;
        this.telefone = telefone;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    @Override
    public String toString() {
        return "Nome: " + nome + " | Telefone: " + telefone;
    }
}

// Gerencia a agenda de contatos
class Agenda {
    private static final String ARQUIVO = "agenda.txt";
    private List<Contato> contatos = new ArrayList<>();

    // Construtor: carrega contatos do arquivo ao iniciar
    public Agenda() {
        carregarContatos();
    }

    // Adiciona um novo contato
    public void adicionarContato(Contato contato) {
        contatos.add(contato);
        salvarContatos();
        System.out.println("Contato adicionado!");
    }

    // Lista todos os contatos
    public void listarContatos() {
        if (contatos.isEmpty()) {
            System.out.println("Nenhum contato encontrado.");
        } else {
            System.out.println("\n--- LISTA DE CONTATOS ---");
            for (Contato c : contatos) {
                System.out.println(c);
            }
        }
    }

    // Busca contatos pelo nome
    public void buscarContato(String nome) {
        boolean encontrado = false;
        for (Contato c : contatos) {
            if (c.getNome().toLowerCase().contains(nome.toLowerCase())) {
                System.out.println("Encontrado -> " + c);
                encontrado = true;
            }
        }
        if (!encontrado) {
            System.out.println("Nenhum contato encontrado.");
        }
    }

    // Remove contato pelo nome
    public void removerContato(String nome) {
        boolean removido = contatos.removeIf(c -> c.getNome().equalsIgnoreCase(nome));
        if (removido) {
            salvarContatos();
            System.out.println("Contato removido!");
        } else {
            System.out.println("Contato não encontrado.");
        }
    }

    // Edita contato pelo nome
    public void editarContato(String nome, String novoNome, String novoTelefone) {
        boolean editado = false;
        for (Contato c : contatos) {
            if (c.getNome().equalsIgnoreCase(nome)) {
                c.setNome(novoNome);
                c.setTelefone(novoTelefone);
                editado = true;
            }
        }
        if (editado) {
            salvarContatos();
            System.out.println("Contato editado!");
        } else {
            System.out.println("Contato não encontrado.");
        }
    }

    // Carrega contatos do arquivo para a memória (lista)
    private void carregarContatos() {
        contatos.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(ARQUIVO))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(";");
                if (dados.length == 2) {
                    contatos.add(new Contato(dados[0], dados[1]));
                }
            }
        } catch (FileNotFoundException e) {
            // Se não existir, tudo bem (cria ao salvar)
        } catch (IOException e) {
            System.out.println("Erro ao carregar contatos: " + e.getMessage());
        }
    }

    // Salva contatos da memória no arquivo
    private void salvarContatos() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARQUIVO))) {
            for (Contato c : contatos) {
                bw.write(c.getNome() + ";" + c.getTelefone());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Erro ao salvar contatos: " + e.getMessage());
        }
    }
}

// Classe principal (menu)
public class AgendaContatos {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Agenda agenda = new Agenda();
        int opcao;

        do {
            System.out.println("\n=== AGENDA DE CONTATOS ===");
            System.out.println("1 - Adicionar Contato");
            System.out.println("2 - Listar Contatos");
            System.out.println("3 - Buscar Contato");
            System.out.println("4 - Remover Contato");
            System.out.println("5 - Editar Contato");
            System.out.println("6 - Sair");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1 -> {
                    System.out.print("Nome: ");
                    String nome = scanner.nextLine();
                    System.out.print("Telefone: ");
                    String telefone = scanner.nextLine();
                    agenda.adicionarContato(new Contato(nome, telefone));
                }
                case 2 -> agenda.listarContatos();
                case 3 -> {
                    System.out.print("Nome para buscar: ");
                    String nome = scanner.nextLine();
                    agenda.buscarContato(nome);
                }
                case 4 -> {
                    System.out.print("Nome para remover: ");
                    String nome = scanner.nextLine();
                    agenda.removerContato(nome);
                }
                case 5 -> {
                    System.out.print("Nome do contato que deseja editar: ");
                    String nome = scanner.nextLine();
                    System.out.print("Novo nome: ");
                    String novoNome = scanner.nextLine();
                    System.out.print("Novo telefone: ");
                    String novoTelefone = scanner.nextLine();
                    agenda.editarContato(nome, novoNome, novoTelefone);
                }
                case 6 -> System.out.println("Encerrando...");
                default -> System.out.println("Opção inválida!");
            }
        } while (opcao != 6);

        scanner.close();
    }
}

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {
	
	/*Para facilitar a visualização e manutenção do código, 
	 essas variáveis podem ser inicializadas fora do escopo do método main*/
	public static List<Produto> carrinho = new ArrayList<Produto>();
	public static List<Venda> vendas = new ArrayList<Venda>();
	 
	public static void main(String[] args) {
		 
		// SIMULANDO BANCO DE DADOS

		//Método para criar e listar Empresas
		//Método para criar e listar Produtos
		List<Empresa> empresas = criaEmpresas();
		
		//Método para criar e listar Clientes
		List<Cliente> clientes = criaClientes();

		//Método para criar e listar Usuários
		//Lista de Clientes e Empresas para ser vinculada aos usuários
		List<Usuario> usuarios = criaUsuarios(clientes, empresas);
		
		executar(usuarios, clientes, empresas);
	}
	
	private static List<Cliente> criaClientes() {
		Cliente cliente = new Cliente("07221134049", "Allan da Silva", "cliente", 20);
		Cliente cliente2 = new Cliente("72840700050", "Samuel da Silva", "cliente2", 24);
		return Arrays.asList(cliente, cliente2);
	}
	
	private static List<Empresa> criaEmpresas(){
		//Criando empresa padaria e seus produtos 
		Empresa empresa = new Empresa(1, "SafeWay Padaria", "30021423000159", 0.15, 0.0);
		empresa.setProduto(1, "Pão Frances", 5, 3.50);
		empresa.setProduto(2, "Sonho", 5, 8.50);
		empresa.setProduto(3, "Croissant", 7, 6.50);
		empresa.setProduto(4, "Chá Gelado", 4, 5.50);

		//cria empresa Varejo e ja referencia seus produtos
		Empresa empresa2 = new Empresa(2, "Level Varejo", "53239160000154", 0.05, 0.0);
		empresa2.setProduto(1, "Coturno", 10, 400.0);
		empresa2.setProduto(2, "Jaqueta Jeans", 15, 150.0);
		empresa2.setProduto(3, "Calça Sarja", 15, 150.0);

		//cria empresa restaurante e ja referencia seus respectivos produtos
		Empresa empresa3 = new Empresa(3, "SafeWay Restaurante", "41361511000116", 0.20, 0.0);
		empresa3.setProduto(1, "Prato feito - Frango", 10, 25.0);
		empresa3.setProduto(2, "Prato feito - Carne", 10, 25.0);
		empresa3.setProduto(3, "Suco Natural", 30, 10.0);

		return Arrays.asList(empresa, empresa2, empresa3);
	}
	
	private static List<Usuario> criaUsuarios(List<Cliente> clientes, List<Empresa> empresas){
		Usuario usuario1 = new Usuario("admin", "1234", null, null);
		Usuario usuario2 = new Usuario("empresa", "1234", null, empresas.get(0));
		Usuario usuario3 = new Usuario("cliente", "1234", clientes.get(0), null);
		Usuario usuario4 = new Usuario("cliente2", "1234", clientes.get(1), null);
		Usuario usuario5 = new Usuario("empresa2", "1234", null, empresas.get(1));
		Usuario usuario6 = new Usuario("empresa3", "1234", null, empresas.get(2));
		return Arrays.asList(usuario1, usuario2, usuario3, usuario4, usuario5, usuario6);
	}

	public static void executar(List<Usuario> usuarios, List<Cliente> clientes, List<Empresa> empresas) {
		Scanner sc = new Scanner(System.in);

		System.out.println("Entre com seu usuário e senha:");
		System.out.print("Usuário: ");
		String username = sc.next();
		System.out.print("Senha: ");
		String senha = sc.next();
		
		//mudei o nome da variavel de usuarioSearch para usuarioLogadoOptional, mais legivel 
		Optional<Usuario> usuarioLogadoOptional = usuarios.stream().filter(usuario -> usuario.getUsername().equals(username)).findFirst();
		//verifica se a variavel usuariologadooptional esta vazia
		if (usuarioLogadoOptional.isPresent()) {
			Usuario usuarioLogado = usuarioLogadoOptional.get();
			if ((usuarioLogado.getSenha().equals(senha))) {

				System.out.println("Escolha uma opção para iniciar");
				if (usuarioLogado.IsEmpresa()) {
					System.out.println("1 - Listar vendas");
					System.out.println("2 - Ver produtos");
					System.out.println("0 - Deslogar");
					Integer escolha = sc.nextInt();
					List<Produto> produtos = usuarioLogado.getEmpresa().getProdutos();
					

					switch (escolha) {
					case 1: {
						System.out.println();
						System.out.println("************************************************************");
						System.out.println("VENDAS EFETUADAS");
						if(vendas.isEmpty())System.out.println("NENHUMA COMPRA EFETUADA");
						else {
						vendas.stream().forEach(venda -> {
							if (venda.getEmpresa().getId().equals(usuarioLogado.getEmpresa().getId())) {
								System.out.println("************************************************************");
								System.out.println("Venda de código: " + venda.getCódigo() + " no CPF "
										+ venda.getCliente().getCpf() + ": ");
								venda.getItens().stream().forEach(x -> {
									System.out.println(x.getId() + " - " + x.getNome() + "    R$" + x.getPreco());
								});
								System.out.println("Total Venda: R$" + venda.getValor());
								System.out.println("Total Taxa a ser paga: R$" + venda.getComissaoSistema());
								System.out.println("Total Líquido  para empresa: "
										+ (venda.getValor() - venda.getComissaoSistema()));
								System.out.println("************************************************************");
							}
						}
						});
						System.out.println("Saldo Empresa: " + usuarioLogado.getEmpresa().getSaldo());
						System.out.println("************************************************************");

						executar(usuarios, clientes, empresas);
					}
					case 2: {
						System.out.println();
						System.out.println("************************************************************");
						System.out.println("MEUS PRODUTOS");
						produtos.stream().forEach(produto -> {
								System.out.println("************************************************************");
								System.out.println("Código: " + produto.getId());
								System.out.println("Produto: " + produto.getNome());
								System.out.println("Quantidade em estoque: " + produto.getQuantidade());
								System.out.println("Valor: R$" + produto.getPreco());								
								System.out.println("************************************************************");
				
						});
						System.out.println("Saldo Empresa: " + usuarioLogado.getEmpresa().getSaldo());
						System.out.println("************************************************************");

						executar(usuarios, clientes, empresas);
					}
					case 0: {
						executar(usuarios, clientes, empresas);

					}
					}

				} else {
					System.out.println("1 - Relizar Compras");
					System.out.println("2 - Ver Compras");
					System.out.println("0 - Deslogar");
					Integer escolha = sc.nextInt();
					switch (escolha) {
					case 1: {
						System.out.println("Para realizar uma compra, escolha a empresa onde deseja comprar: ");
						for(Empresa empresa : empresas) {
							System.out.println(empresa.getId() + " - " + empresa.getNome());
						}
						//a abordagem do primeiro é mais simples, é so uma impressão de dados
						//empresas.stream().forEach(empresa -> {
						//	System.out.println(x.getId() + " - " + x.getNome());
						//});
						int escolhaEmpresa = sc.nextInt();
						//optional é uma classe que ajuda a evitar null pointer 
						//estou pegando a empresa escolhida aqui agora
						Optional<Empresa> empresaEscolhidaOptional = empresas.stream().
							filter(empresa -> empresa.getId().equals(escolhaEmpresa)).findFirst();
						
						Empresa empresaEscolhida = empresaEscolhidaOptional.get();
						List<Produto> produtos = empresaEscolhida.getProdutos();
						int idProduto;						
						do {
							System.out.println("Escolha os seus produtos: ");
							produtos.stream().forEach(produto ->{
								System.out.println(produto.getId() + " - " + produto.getNome());
							});
							
							System.out.println("0 - Finalizar compra");
							idProduto = sc.nextInt();
							for (Produto produtoSearch : produtos) {
								if (produtoSearch.getId().equals(idProduto))
									carrinho.add(produtoSearch);
							}
						} while (idProduto != 0);
						System.out.println("************************************************************");
						System.out.println("Resumo da compra: ");
						carrinho.stream().forEach(car ->
							// não precisa mais dessa validação pois produto pertence a empresa ja
							//if (x.getEmpresa().getId().equals(escolhaEmpresa)) {
							// assim como if, quando o arrow function contem so uma linha não precisa abrir e fechar chaves
								System.out.println(car.getId() + " - " + car.getNome() + "    R$" + car.getPreco())
						);
						//não é necessário aquele codigo, pois cliente contem no obj usuario
						//o codigo antigo estava da seguinte forma 
						//Cliente clienteLogado = clientes.stream()
						//.filter(x -> x.getUsername().equals(usuarioLogado.getUsername()))
						//.collect(Collectors.toList()).get(0);
						Cliente cliente = usuarioLogado.getCliente();
						
						Venda venda = criarVenda(carrinho, empresaEscolhida, cliente, vendas);
						System.out.println("Total: R$" + venda.getValor());
						System.out.println("************************************************************");
						carrinho.clear();
						executar(usuarios, clientes, empresas);
					}
					case 2: {
						System.out.println();
						System.out.println("************************************************************");
						if(vendas.isEmpty()) System.out.println("NENHUMA COMPRA EFETUADA");
						else {
						System.out.println("COMPRAS EFETUADAS");
						vendas.stream().forEach(venda -> {
							if (venda.getCliente().getUsername().equals(usuarioLogado.getUsername())) {
								System.out.println("************************************************************");
								System.out.println("Compra de código: " + venda.getCódigo() + " na empresa "
										+ venda.getEmpresa().getNome() + ": ");
								venda.getItens().stream().forEach(x -> {
									System.out.println(x.getId() + " - " + x.getNome() + "    R$" + x.getPreco());
								});
								System.out.println("Total: R$" + venda.getValor());
								System.out.println("************************************************************");
							}

						});
						}

						executar(usuarios, clientes, empresas);
					}
					case 0: {
						executar(usuarios, clientes, empresas);

					}

					}
				}

			} else
				System.out.println("Senha incorreta");
		} else {
			System.out.println("Usuário não encontrado");
		}
		executar(usuarios, clientes, empresas);
	}

	public static Venda criarVenda(List<Produto> carrinho, Empresa empresa, Cliente cliente, List<Venda> vendas) {
		Double total = carrinho.stream().mapToDouble(Produto::getPreco).sum();
		Double comissaoSistema = total * empresa.getTaxa();
		int idVenda = vendas.isEmpty() ? 1 : vendas.get(vendas.size() - 1).getCódigo() + 1;
		Venda venda = new Venda(idVenda, carrinho.stream().toList(), total, comissaoSistema, empresa, cliente);
		empresa.setSaldo(empresa.getSaldo() + total);
		vendas.add(venda);
		return venda;
	}
}

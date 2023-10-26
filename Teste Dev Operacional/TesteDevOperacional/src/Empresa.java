import java.util.ArrayList;
import java.util.List;

public class Empresa {
	private Integer id;
	private String nome;
	private String cnpj;
	private Double taxa;
	private Double saldo;
	//produtos da empresa (referencia)
	private List<Produto> produtos = new ArrayList<Produto>();

	public Empresa() {
		super();
	}

	public Empresa(Integer id, String nome, String cnpj, Double taxa, Double saldo) {
		super();
		this.id = id;
		this.nome = nome;
		this.cnpj = cnpj;
		this.taxa = taxa;
		this.saldo = saldo;
	}
	
	public List<Produto> getProdutos(){
		return this.produtos;
	}

	public void setProduto(Integer id,String nome, Integer quantidade, Double preco) {
		Produto produto = new Produto(id, nome, quantidade, preco);
		this.produtos.add(produto);
	}
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public Double getTaxa() {
		return taxa;
	}

	public void setTaxa(Double taxa) {
		this.taxa = taxa;
	}

	public Double getSaldo() {
		return saldo;
	}

	public void setSaldo(Double saldo) {
		this.saldo = saldo;
	}

}

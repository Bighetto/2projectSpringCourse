package course.SpringBiga;

import course.SpringBiga.Domain.*;
import course.SpringBiga.Domain.Enums.EstadoPagamento;
import course.SpringBiga.Domain.Enums.TipoCliente;
import course.SpringBiga.Repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


import java.text.SimpleDateFormat;
import java.util.Arrays;


@SpringBootApplication
public class SpringBigaApplication implements CommandLineRunner {

	@Autowired
	private CategoriaRepository categoriaRepository;
	@Autowired
	private ProdutoRepository produtoRepository;
	@Autowired
	private EstadoRepository estadoRepository;
	@Autowired
	private CidadeRepository cidadeRepository;
	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private EnderecoRepository enderecoRepository;
	@Autowired
	private PedidoRepository pedidoRepository;
	@Autowired
	private PagamentoRepository pagamentoRepository;
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;

	private Produto cat = new Produto();

	public static void main(String[] args) {
		SpringApplication.run(SpringBigaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		Categoria cat1 = new Categoria(null, "Informatica");
		Categoria cat2 = new Categoria(null, "Escritorio");

		Produto p1 = new Produto(null, "Computador", 2000.00);
		Produto p2 = new Produto(null, "Impressora", 800.00);
		Produto p3 = new Produto(null, "Mouse", 80.00);

		categoriaRepository.saveAll(Arrays.asList(cat1,cat2));

		cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3));
		cat2.getProdutos().addAll(Arrays.asList(p2));

		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat2, cat1));
		p3.getCategorias().addAll(Arrays.asList(cat1));

		produtoRepository.saveAll(Arrays.asList(p1, p2, p3));

		Estado est1 = new Estado(null,"Minas Gerais");
		Estado est2 = new Estado(null, "São Paulo");

		Cidade c1 = new Cidade(null, "Uberlândia", est1);
		Cidade c2 = new Cidade(null,"Jundiaí", est2);
		Cidade c3 = new Cidade(null, "Campinas", est2);

		est1.getCidades().addAll(Arrays.asList(c1));
		est2.getCidades().addAll(Arrays.asList(c2,c3));

		estadoRepository.saveAll(Arrays.asList(est1,est2));
		cidadeRepository.saveAll(Arrays.asList(c1,c2,c3));

		Cliente cli1 = new Cliente(null, "Arthur Bighetto", "arthurbighetto@gmail.com", "35896547145", TipoCliente.PESSOAFISICA);

		cli1.getTelefones().addAll(Arrays.asList("48572698", "11987542568"));

		Endereco e1 = new Endereco(null, "Nove de julho", "578", "Apto 505", "Jardim do Lago", "65729830", cli1, c2);
		Endereco e2 = new Endereco(null, "Rua cica", "915", "CASA", "São Camilo", "35214789", cli1, c1);

		cli1.getEnderecos().addAll(Arrays.asList(e1,e2));

		clienteRepository.saveAll(Arrays.asList(cli1));
		enderecoRepository.saveAll(Arrays.asList(e1, e2));

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

		Pedido ped1 = new Pedido(null, sdf.parse("28/10/2021 22:23"), cli1, e1);
		Pedido ped2 = new Pedido(null, sdf.parse("30/08/2021 13:37"), cli1, e2);

		Pagamento pagto1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 5);
		ped1.setPagamento(pagto1);

		Pagamento pagto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2, sdf.parse("01/09/2021 00:00"), null);
		ped2.setPagamento(pagto2);

		cli1.getPedidos().addAll(Arrays.asList(ped1,ped2));

		pedidoRepository.saveAll(Arrays.asList(ped1, ped2));
		pagamentoRepository.saveAll(Arrays.asList(pagto1, pagto2));

		ItemPedido ip1 = new ItemPedido(ped1,  p1,0.0,  1, 2000.0);
		ItemPedido ip2 = new ItemPedido(ped2, p3,0.0, 2, 80.0);
		ItemPedido ip3 = new ItemPedido(ped2, p2,100.0 , 1, 800.0);

		ped1.getItens().addAll(Arrays.asList(ip1, ip2));
		ped2.getItens().addAll(Arrays.asList(ip3));

		p1.getItens().addAll(Arrays.asList(ip1));
		p2.getItens().addAll(Arrays.asList(ip3));
		p3.getItens().addAll(Arrays.asList(ip2));

		itemPedidoRepository.saveAll(Arrays.asList(ip1,ip2,ip3));


	}
}
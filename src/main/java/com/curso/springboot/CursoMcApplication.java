package com.curso.springboot;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.curso.springboot.domain.Categoria;
import com.curso.springboot.domain.Cidade;
import com.curso.springboot.domain.Cliente;
import com.curso.springboot.domain.Endereco;
import com.curso.springboot.domain.Estado;
import com.curso.springboot.domain.ItemPedido;
import com.curso.springboot.domain.Pagamento;
import com.curso.springboot.domain.PagamentoComBoleto;
import com.curso.springboot.domain.PagamentoComCartao;
import com.curso.springboot.domain.Pedido;
import com.curso.springboot.domain.Produto;
import com.curso.springboot.domain.enums.EstadoPagamento;
import com.curso.springboot.domain.enums.TipoCliente;
import com.curso.springboot.repositories.CategoriaRepository;
import com.curso.springboot.repositories.CidadeRepository;
import com.curso.springboot.repositories.ClienteRepository;
import com.curso.springboot.repositories.EnderecoRepository;
import com.curso.springboot.repositories.EstadoRepository;
import com.curso.springboot.repositories.ItemPedidoRepository;
import com.curso.springboot.repositories.PagamentoRepository;
import com.curso.springboot.repositories.PedidoRepository;
import com.curso.springboot.repositories.ProdutoRepository;

@SpringBootApplication
public class CursoMcApplication implements CommandLineRunner {

	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private EstadoRepository estadoRepository;
	
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
	
	public static void main(String[] args) {
		SpringApplication.run(CursoMcApplication.class, args);
	}

	/**
	 * metodo implemantado pelo comandlineruner 
	 * para inicializacao do springboot e execucao
	 */
	@Override
	public void run(String... args) throws Exception {
		
		
		Categoria cat1  = new Categoria(null, "informatica");
		Categoria cat2  = new Categoria(null, "escritorio");
		
		Produto p1 = new Produto(null, "computador", 2000.00);
		Produto p2 = new Produto(null, "impressora", 800.00);
		Produto p3 = new Produto(null, "mause", 80.00);
		
		cat1.getProdutos().addAll(Arrays.asList(p1,p2,p3));
		cat2.getProdutos().addAll(Arrays.asList(p2));
		
		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1,cat2));
		p3.getCategorias().addAll(Arrays.asList(cat1));
		
		this.categoriaRepository.saveAll(Arrays.asList(cat1,cat2));
		
		this.produtoRepository.saveAll(Arrays.asList(p1,p2,p3));
		
		Estado est1 = new Estado(null, "minas gerais");
		Estado est2 = new Estado(null, "são paulo");
		
		Cidade c1 = new Cidade(null, "Uberlandia", est1);
		Cidade c2 = new Cidade(null, "São Paulo", est2);
		Cidade c3 = new Cidade(null, "Campinas", est2);
		
		est1.getCidades().addAll(Arrays.asList(c1));
		est2.getCidades().addAll(Arrays.asList(c2,c3));
		
		
		this.estadoRepository.saveAll(Arrays.asList(est1,est2));
		this.cidadeRepository.saveAll(Arrays.asList(c1,c2,c3));
		
		
	Cliente cli1 = new Cliente(null, "Maria Silva", "maria@gmail.com", "36378912377", TipoCliente.PESSOAFISICA);
	
	cli1.getTelefones().addAll(Arrays.asList("27363323","93838393"));
	
	Endereco e1 = new Endereco(null, "Rua Flores", "300", "Apto 203", "Jardim", "38220834", cli1, c1);
	
	Endereco e2 = new Endereco(null, "Avenida Matos", "105", "Sala 800", "Centro", "38777012", cli1, c2);
	
	cli1.getEnderecos().addAll(Arrays.asList(e1,e2));
	
	this.clienteRepository.saveAll(Arrays.asList(cli1));
	this.enderecoRepository.saveAll(Arrays.asList(e1,e2));
	
	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	
	
	Pedido ped1 = new Pedido(null, sdf.parse("30/09/2017 10:32"), cli1, e1);
	
	Pedido ped2 = new Pedido(null, sdf.parse("10/10/2017 19:35"), cli1, e2);
	
	Pagamento pagto1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 6);
	ped1.setPagamento(pagto1);
	
	Pagamento pagto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2, sdf.parse("20/10/2017 00:00"), null);
	ped2.setPagamento(pagto2);
	
	cli1.getPedidos().addAll(Arrays.asList(ped1,ped2));
	
	
	this.pedidoRepository.saveAll(Arrays.asList(ped1,ped2));
	
	this.pagamentoRepository.saveAll(Arrays.asList(pagto1,pagto2));
	
	ItemPedido ip1 = new ItemPedido(ped1, p1, 0.00, 1, 2000.00);
	
	ItemPedido ip2 = new ItemPedido(ped1, p3, 0.00, 2, 80.00);
	
	ItemPedido ip3 = new ItemPedido(ped2, p2, 100.00, 1, 800.00);
	
	ped1.getItens().addAll(Arrays.asList(ip1,ip2));
	
	ped2.getItens().addAll(Arrays.asList(ip3));
	
	
	p1.getItens().addAll(Arrays.asList(ip1));
	
	p2.getItens().addAll(Arrays.asList(ip3));
	
	p3.getItens().addAll(Arrays.asList(ip2));
	
	
	this.itemPedidoRepository.saveAll(Arrays.asList(ip1,ip2,ip3));
	
	
	}

}

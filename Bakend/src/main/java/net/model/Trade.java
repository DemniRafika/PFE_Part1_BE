package net.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document (collection = "Trade")
@XmlRootElement (name="Trade")
public class Trade {
	
	@Transient
    public static final String SEQUENCE_NAME = "traders_sequence";
	
	@Id
	private long id;
	
	/*@NotBlank
    @Size(max=100)
	@Indexed(unique=true)
	*/
	private String firm;
	private String date;
	private String product;
	private String contract;
	private String bs;
	private String qty;
	private String price;
	private String account;
	private String order;
	private String org;
	private String cti;
	private String tradedAs;
	private String orderType;
	private String bkr;
	private String oppFirm;
	private String oppBrkr;
	private String timeBrkt;
	
    public Trade() {
		
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFirm() {
		return firm;
	}

	public void setFirm(String firm) {
		this.firm = firm;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public String getContract() {
		return contract;
	}

	public void setContract(String contract) {
		this.contract = contract;
	}

	public String getBs() {
		return bs;
	}

	public void setBs(String bs) {
		this.bs = bs;
	}

	public String getQty() {
		return qty;
	}

	public void setQty(String qty) {
		this.qty = qty;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public String getOrg() {
		return org;
	}

	public void setOrg(String org) {
		this.org = org;
	}

	public String getCti() {
		return cti;
	}

	public void setCti(String cti) {
		this.cti = cti;
	}

	public String getTradedAs() {
		return tradedAs;
	}

	public void setTradedAs(String tradedAs) {
		this.tradedAs = tradedAs;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getBkr() {
		return bkr;
	}

	public void setBkr(String bkr) {
		this.bkr = bkr;
	}

	public String getOppFirm() {
		return oppFirm;
	}

	public void setOppFirm(String oppFirm) {
		this.oppFirm = oppFirm;
	}

	public String getOppBrkr() {
		return oppBrkr;
	}

	public void setOppBrkr(String oppBrkr) {
		this.oppBrkr = oppBrkr;
	}

	public String getTimeBrkt() {
		return timeBrkt;
	}

	public void setTimeBrkt(String timeBrkt) {
		this.timeBrkt = timeBrkt;
	}

	public static String getSequenceName() {
		return SEQUENCE_NAME;
	}

	public Trade(long id, String firm, String date, String product, String contract, String bs, String qty,
			String price, String account, String order, String org, String cti, String tradedAs, String orderType,
			String bkr, String oppFirm, String oppBrkr, String timeBrkt) {
		super();
		this.id = id;
		this.firm = firm;
		this.date = date;
		this.product = product;
		this.contract = contract;
		this.bs = bs;
		this.qty = qty;
		this.price = price;
		this.account = account;
		this.order = order;
		this.org = org;
		this.cti = cti;
		this.tradedAs = tradedAs;
		this.orderType = orderType;
		this.bkr = bkr;
		this.oppFirm = oppFirm;
		this.oppBrkr = oppBrkr;
		this.timeBrkt = timeBrkt;
	}

	

    
  
	

}

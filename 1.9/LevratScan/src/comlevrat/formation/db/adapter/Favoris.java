package comlevrat.formation.db.adapter;

/**
 * Objet Course
 * @author AcE
 *
 */
public class Favoris {

	private int id;
	private String produit;
	private String price;
	private String description;
	private String ean13;
		
	public Favoris() {
		this.produit = "";
		this.price = "";
		this.description = "";
		this.ean13 = "";
	}
	
	public Favoris(String produit, String price , String description, String ean13) {
		this.produit = produit;
		this.price = price;
		this.description = description;
		this.ean13 = ean13;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getProduit() {
		return produit;
	}
	public void setProduit(String produit) {
		this.produit = produit;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getEan13() {
		return ean13;
	}
	public void setEan13(String ean13) {
		this.ean13 = ean13;
	}
}



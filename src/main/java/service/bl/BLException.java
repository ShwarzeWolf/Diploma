package service.bl;

public class BLException extends Exception {
    private static final long serialVersionUID = -5782463391020990879L;

    public BLException(String msg) {
		super(msg);
	}
	public BLException(Exception ex) {
		super(ex);
	}
	public BLException(String msg, Exception ex) {
		super(msg, ex);
	}
}
package Server;

public enum HttpStatus
{
	OK(200,"OK"), 
	BADREQUEST(400, "Bad Request"),
	NFOUND(404, "Not Found"), 
	NMODIFIED(304, "Not Modified"),
	FORBIDDEN(403,"Access is Forbidden");

	
	private int value;
	private String msg;
	HttpStatus(int stat, String message){
		this.value=stat;
		this.msg = message;
	}
	@Override
	  public String toString() {
		return "HTTP/1.0 "+ this.value + " " + this.msg;
	 }
}

package xyz.symhx.normal.qrcode;

public class QrCodeGenerateException extends RuntimeException {

	private static final long serialVersionUID = -3123966199040432229L;

	public QrCodeGenerateException() {
		super();
	}

	public QrCodeGenerateException(Throwable cause) {
		super(cause);
	}

	public QrCodeGenerateException(final String message) {
		super(message);
	}

	public QrCodeGenerateException(final String message, Throwable cause) {
		super(message, cause);
	}

}

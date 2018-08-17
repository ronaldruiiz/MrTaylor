package aplicacion.android.ronal.serietaylor.data;

public class Results {

    private String mTitulo;
    private String url;

    public Results(String mTitulo, String url) {
        this.mTitulo = mTitulo;
        this.url = url;
    }

    public String getmTitulo() {
        return mTitulo;
    }

    public void setmTitulo(String mTitulo) {
        this.mTitulo = mTitulo;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}

package vn.iotstarts.Model;

public class Info {
	private String key;
    private String value;

    public Info(String key, String value) {
        this.key = key;
        this.value = value;
    }

    // getter và setter
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}

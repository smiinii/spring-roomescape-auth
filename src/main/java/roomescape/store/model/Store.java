package roomescape.store.model;

public class Store {

    private final Long id;
    private final String storeNumber;
    private final String name;

    public Store(Long id, String storeNumber, String name) {
        this.id = id;
        this.storeNumber = storeNumber;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getStoreNumber() {
        return storeNumber;
    }

    public String getName() {
        return name;
    }
}

package roomescape.store.dto;

import roomescape.store.model.Store;

public class StoreResponse {

    private final Long id;
    private final String name;

    private StoreResponse(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public static StoreResponse from(Store store) {
        return new StoreResponse(store.getId(), store.getName());
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}

package roomescape.store.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.store.dto.StoreResponse;
import roomescape.store.repository.StoreRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class StoreService {

    private final StoreRepository storeRepository;

    public StoreService(StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }

    public List<StoreResponse> findAll() {
        return storeRepository.findAll().stream()
                .map(StoreResponse::from)
                .toList();
    }
}

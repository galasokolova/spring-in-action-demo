package pt.galina.chap_15_actuatordemo.config.actuator.info;

import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Component;
import pt.galina.chap_15_actuatordemo.entity.taco.data.TacoRepository;

import java.util.HashMap;
import java.util.Map;

@Component
public class TacoCountInfoContributor implements InfoContributor {

    private final TacoRepository tacoRepo;

    public TacoCountInfoContributor(TacoRepository tacoRepo) {
        this.tacoRepo = tacoRepo;
    }

    @Override
    public void contribute(Info.Builder builder) {
        // Returning the blocking result temporary for synchronous reply with Actuator
        long tacoCount = tacoRepo.count().blockOptional().orElse(0L);

        Map<String, Object> tacoMap = new HashMap<>();
        tacoMap.put("count", tacoCount);
        builder.withDetail("taco-stats", tacoMap);

        builder.withDetail("contact",
                Map.of("email", "support@tacocloud.com", "phone", "822-625-6831"));
    }
}

package pt.galina.clientnonreactive.config.actuator.info;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Component;
import pt.galina.clientnonreactive.data.TacoRepository;

@Component
public class TacoCountInfoContributor implements InfoContributor {

    private final TacoRepository tacoRepo;

    public TacoCountInfoContributor(TacoRepository tacoRepo) {
        this.tacoRepo = tacoRepo;
    }

    @Override
    public void contribute(Info.Builder builder) {
        long tacoCount = tacoRepo.count();

        Map<String, Object> tacoMap = new HashMap<>();
        tacoMap.put("count", tacoCount);
        builder.withDetail("taco-stats", tacoMap);

        builder.withDetail("contact",
                Map.of("email", "support@tacocloud.com", "phone", "822-625-6831"));
    }
}


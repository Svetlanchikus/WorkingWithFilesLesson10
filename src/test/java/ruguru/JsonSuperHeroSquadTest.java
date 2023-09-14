package ruguru;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ruguru.model.SuperHeroSquadModel;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonSuperHeroSquadTest {
    @Test
    @DisplayName("Checking the contents of a JSON file")
    public void testJsonParsing() throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        File jsonFile = new File("src/test/resources/Super_hero_squad.json");
        List<SuperHeroSquadModel> superHeroSquadGet = objectMapper.readValue(jsonFile, new TypeReference<>() {});

        assertEquals(3, superHeroSquadGet.size());

        SuperHeroSquadModel superHeroSquad1 = superHeroSquadGet.get(0);
        assertEquals("Molecule Man", superHeroSquad1.getName());
        assertEquals(29, superHeroSquad1.getAge());
        assertEquals("Dan Jukes", superHeroSquad1.getSecretIdentity());
        assertEquals("Radiation resistance", superHeroSquad1.getPowers());

        SuperHeroSquadModel superHeroSquad2 = superHeroSquadGet.get(1);
        assertEquals("Madame Uppercut", superHeroSquad2.getName());
        assertEquals(39, superHeroSquad2.getAge());
        assertEquals("Jane Wilson", superHeroSquad2.getSecretIdentity());
        assertEquals("Million tonne punch", superHeroSquad2.getPowers());

        SuperHeroSquadModel superHeroSquad3 = superHeroSquadGet.get(2);
        assertEquals("Eternal Flame", superHeroSquad3.getName());
        assertEquals(1000000, superHeroSquad3.getAge());
        assertEquals("Unknown", superHeroSquad3.getSecretIdentity());
        assertEquals("Immortality", superHeroSquad3.getPowers());
    }
}

package com.zed.projectz;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class RaceHelper {
    private List<Race> Races = new ArrayList<>(Arrays.asList(
            new Race("Sardakk N'Orr", 0),
            new Race("The Arborec", 1),
            new Race("The Barony of Letnev", 2),
            new Race("The Clan of Saar", 3),
            new Race("The Embers of Muaat", 4),
            new Race("The Emirates of Hacan", 5),
            new Race("The Federation of Sol", 6),
            new Race("The Ghosts of Creuss", 7),
            new Race("The L1z1x Mindnet", 8),
            new Race("The Mentak Coalition", 9),
            new Race("The Naalu Collective", 10),
            new Race("The Nekro Virus", 11),
            new Race("The Universities of Jol-Nar", 12),
            new Race("The Winnu", 13),
            new Race("The Xxcha Kingdom", 14),
            new Race("The Yin Brotherhood", 15),
            new Race("The Yssaril Tribes", 16)
    ));

    public List<Player> RandomizeRaces(List<Player> players, int numberOfRacesPerPlayer) {
        if (numberOfRacesPerPlayer * players.size() > 17) {
            numberOfRacesPerPlayer = 1;
        }
        List<Race> racesClone = new ArrayList<>(Races.size());
        for (Race race : Races) {
            racesClone.add(new Race(race));
        }
        for (Player player : players) {
            player.RaceOptions = new ArrayList<>();
            for (int i = 0; i < numberOfRacesPerPlayer; i++) {
                Random random = new Random();
                int randomNumber = random.nextInt(racesClone.size());
                Race randomRace = racesClone.get(randomNumber);
                racesClone.remove(randomNumber);
                player.RaceOptions.add(randomRace);
            }
        }
        return players;
    }
}
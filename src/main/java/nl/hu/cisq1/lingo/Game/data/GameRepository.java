package nl.hu.cisq1.lingo.Game.data;

import nl.hu.cisq1.lingo.Game.domain.Game;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<Game, Long> {

}
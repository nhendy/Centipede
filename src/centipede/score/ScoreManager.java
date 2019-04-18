package centipede.score;



public class ScoreManager {
    
    private int _score;

    private static int HIT_CENTIPEDE      = 2;
    private static int KILL_CENTIPEDE_SEG = 5;
    private static int HIT_MUSHROOM       = 1;
    private static int KILL_MUSHROOM      = 5;
    private static int RESTORE_MUSHROOM   = 10;
    private static int HIT_SPIDER         = 100;
    private static int KILL_SPIDER        = 600;
    private static int KILL_CENTIPEDE     = 600;


    public ScoreManager(){
        this._score = 0 ;
    }

    public void playerHitsCentipede(){  ///
        _score += HIT_CENTIPEDE; 
    }
    public void playerKillsCentipedeSegment(){  ///
        _score += KILL_CENTIPEDE_SEG;
    }
    public void playerKillsCentipede(){    ///
        _score += KILL_CENTIPEDE;
    }
    public void playerKillsMushroom(){ ///
        _score += KILL_MUSHROOM;
    }
    public void playerRestoresMushroom(){   ///
        _score += RESTORE_MUSHROOM;
    }
    public void playerHitsMushroom(){   ///
        _score += HIT_MUSHROOM;
    }
    public void playerKillsSpider(){  ///
        _score += KILL_SPIDER;
    }
    public void playerHitsSpider(){  ///
        _score += HIT_SPIDER;
    }


    public int getScore(){
        return _score;
    }
}
package com.mysaml.mc.board;

import com.mysaml.mc.api.MySamlAddon;

public class Main extends MySamlAddon {
    @Override
    public void onEnabled() {
        this.addCommand("fly", new FlyCommand());
        try {
            ScoreBoardListener scoreListener = new ScoreBoardListener(getCore());
            this.addEventListener("score", scoreListener);
            this.addCommand("scoreboard", new ScoreBoardCommand(scoreListener));
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onDisabled() {
        System.out.println("DCI finalizado");
    }
}
package uet.oop.bomberman.entities;

import java.awt.image.BufferedImage;

class MonsterController {
    private Monster monster;

    public MonsterController(Monster monster) {
        this.monster = monster;
    }
    // chiu huhu ko di chuyen duoc de mai lam tiep :(( 
    public void MonsterMove() {
        if(!this.monster.isDead()) {
            if(!this.monster.solidRight ) {
                this.monster.moveRight();
            } else {
                if (!this.monster.solidLeft) {
                    this.monster.moveLeft();
                } else {
                    if (!this.monster.solidUp) {
                        this.monster.moveUp();
                    } else {
                        if (!this.monster.solidDown) {
                            this.monster.moveDown();
                        }
                    }
                }
            }
        }
    }
}

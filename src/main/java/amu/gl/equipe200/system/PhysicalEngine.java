package amu.gl.equipe200.system;

import amu.gl.equipe200.entity.BaseEntity;

import java.util.List;

public class PhysicalEngine {
    public static void moveEntity(List<BaseEntity> entityList){
        for(BaseEntity entity : entityList){
            entity.move();
        }
    }

}

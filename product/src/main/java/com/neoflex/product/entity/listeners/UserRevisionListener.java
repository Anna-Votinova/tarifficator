package com.neoflex.product.entity.listeners;
import com.neoflex.product.entity.Revision;
import org.hibernate.envers.RevisionListener;

public class UserRevisionListener implements RevisionListener {

    private static final String USER = "ADMIN";
    @Override
    public void newRevision(Object revisionEntity) {
        Revision revision = (Revision) revisionEntity;
        revision.setUsername(USER);
    }
}

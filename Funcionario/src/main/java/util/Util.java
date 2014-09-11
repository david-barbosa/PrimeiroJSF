/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import org.hibernate.Session;
import org.primefaces.context.RequestContext;

/**
 *
 * @author David
 */
public class Util {

    public static Session pegarSessao() {
        return HibernateUtil.getSessionFactory().getCurrentSession();
    }

    public static void resetarFormulario(String idForm) {
        RequestContext.getCurrentInstance().reset(idForm);
    }
}

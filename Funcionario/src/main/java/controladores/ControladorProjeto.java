/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import entidades.Funcionario;
import entidades.Projeto;
import java.util.List;
import util.HibernateDAO;
import util.HibernateUtil;
import util.Util;
import util.UtilErros;
import util.UtilMensagens;

/**
 *
 * @author David
 */
public class ControladorProjeto {

    private HibernateDAO<Projeto> dao;

    public ControladorProjeto() {
        //this.dao = new HibernateDAO<>(Util.pegarSessao(), Projeto.class);
        this.dao = new HibernateDAO<>(HibernateUtil.getSessionFactory().getCurrentSession(),Projeto.class);
    }

    private Projeto carregar(String hql) {
        return this.dao.carregar(hql);
    }

    public boolean salvar(Projeto projeto) {
        try {
            if (projeto.getId() == null) {
                this.dao.salvar(projeto);
                UtilMensagens.mensagemInformacao("Projeto salvo");
                return true;
            } else {
                String hql = "SELECT vo FROM Projeto vo"
                        + " WHERE vo.nome ='" + projeto.getNome() + "'"
                        + " AND vo.id != " + projeto.getId() + "";
                Projeto verifica = new ControladorProjeto().carregar(hql);
                if (verifica == null) {
                    this.dao.atualizar(projeto);
                    UtilMensagens.mensagemInformacao("Projeto atualizado");
                    return true;
                } else {
                    String hql2 = "SELECT vo FROM Projeto vo"
                            + " WHERE vo.id = " + projeto.getId() + "";
                    Projeto projetoAtual = new ControladorProjeto().carregar(hql2);
                    UtilMensagens.mensagemInformacao("projeto: " + projeto.getNome() + "já existe");
                    projeto.setNome(projetoAtual.getNome());
                    return false;
                }
            }
        } catch (Exception e) {
            UtilMensagens.mensagemErro("Erro ao salvar o projeto:" + UtilErros.getMensagemErro(e));
            return false;
        }
    }
    
    public void excluir(Projeto projeto){
        try {
            //String hql = "DELETE vo FROM Funcionario vo WHERE vo.projeto='" + projeto.getId() + "'";
            //this.dao.comando(hql, projeto.getId());
            this.dao.excluir(projeto);
            UtilMensagens.mensagemInformacao("Projeto e Funcionários excluidos");
        } catch (Exception e) {
            UtilMensagens.mensagemErro("Erro ao excluir o projeto: " + UtilErros.getMensagemErro(e));
        }
    }
    
    public List<Projeto> listar(String hql){
        return this.dao.listar(hql);
    }   
}
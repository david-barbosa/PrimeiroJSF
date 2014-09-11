/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import entidades.Funcionario;
import java.util.List;
import util.HibernateDAO;
import util.HibernateUtil;
import util.UtilErros;
import util.UtilMensagens;

/**
 *
 * @author David
 */
public class ControladorFuncionario {

    private HibernateDAO<Funcionario> dao;

    public ControladorFuncionario() {
        //this.dao = new HibernateDAO<>(Util.pegarSessao(), Funcionario.class);
        this.dao = new HibernateDAO<>(HibernateUtil.getSessionFactory().getCurrentSession(), Funcionario.class);
    }

    private Funcionario carregar(String hql) {
        return this.dao.carregar(hql);
    }
    
    public List<Funcionario> listar(String hql) {
        return this.dao.listar(hql);
    }

    public boolean salvar(Funcionario funcionario) {
        try {
            if (funcionario.getId() == null) {
                    this.dao.salvar(funcionario);
                    UtilMensagens.mensagemInformacao("Funcionario salvo");
                    return true;
            } else {
                String hql = "SELECT vo FROM Funcionario vo"
                        + " WHERE vo.nome ='" + funcionario.getEmail() + "'"
                        + " AND vo.id != " + funcionario.getId() + "";
                Funcionario verifica = new ControladorFuncionario().carregar(hql);
                if (verifica == null) {
                        this.dao.atualizar(funcionario);
                        UtilMensagens.mensagemErro("Funcionario atualizado");
                        return true;
                } else {
                    String hql2 = "SELECT vo FROM Funcionario vo"
                            + " WHERE vo.id = " + funcionario.getId() + "";
                    Funcionario funcionarioAtual = new ControladorFuncionario().carregar(hql2);
                    UtilMensagens.mensagemInformacao("funcionário: " + funcionario.getNome() + "já existe");
                    funcionario.setNome(funcionarioAtual.getNome());
                    return false;
                }
            }
        } catch (Exception e) {
            UtilMensagens.mensagemErro("Erro ao salvar o funcionário:" + UtilErros.getMensagemErro(e));
            return false;
        }
    }

    public void excluir(Funcionario funcionario) {
        try {
            this.dao.excluir(funcionario);
            UtilMensagens.mensagemInformacao("Funcionario excluido");
        } catch (Exception e) {
            UtilMensagens.mensagemErro("Erro ao excluir o funcionário: " + UtilErros.getMensagemErro(e));
        }
    }
}

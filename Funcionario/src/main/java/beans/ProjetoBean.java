/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import controladores.ControladorFuncionario;
import controladores.ControladorProjeto;
import entidades.Funcionario;
import entidades.Projeto;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import util.Util;
import static util.Util.resetarFormulario;

/**
 *
 * @author David
 */
@ManagedBean
@ViewScoped
public class ProjetoBean implements Serializable {

    private Projeto projeto;
    private Funcionario funcionario;
    private List<Projeto> listaProjeto;
    private List<Funcionario> listaFuncionario;
    private String textoBusca;
    private String colunaBusca;

    @PostConstruct
    public void init() {
        this.projeto = new Projeto();
        this.funcionario = new Funcionario();
        this.colunaBusca = "nome";
    }

    public void salvar() {
        if (new ControladorProjeto().salvar(this.projeto)) {
            this.listaProjeto = null;
            this.projeto = new Projeto();
            resetarFormulario("form");
        }
    }

    public void cancelar() {
        this.projeto = new Projeto();
        resetarFormulario("form");
    }

    public void pesquisar() {
        String hql = "SELECT projeto FROM Projeto projeto WHERE CAST (projeto." + this.colunaBusca + " AS text) LIKE '" + this.textoBusca + "%'";
        this.listaProjeto = new ControladorProjeto().listar(hql);
    }

    public void pesquisarFuncionario() {
        String hql = "SELECT funcionario FROM Funcionario funcionario WHERE funcionario.projeto= '" + this.textoBusca + "' ORDER BY funcionario.nome DESC";
        this.listaFuncionario = new ControladorFuncionario().listar(hql);
    }

    public void editar(Projeto projeto) {
        this.projeto = projeto;
        resetarFormulario("form");
    }

    public void excluir(Projeto projeto) {
        new ControladorProjeto().excluir(projeto);
        this.listaProjeto = null;
        this.listaFuncionario = null;
    }

    public Projeto getProjeto() {
        return projeto;
    }

    public void setProjeto(Projeto projeto) {
        this.projeto = projeto;
    }

    public List<Projeto> getListaProjeto() {
        if (this.listaProjeto == null) {
            String hql = "SELECT vo FROM Projeto vo"
                    + " ORDER BY vo.id DESC";
            this.listaProjeto = new ControladorProjeto().listar(hql);
        }
        return listaProjeto;
    }

    public void setListaProjeto(List<Projeto> listaProjeto) {
        this.listaProjeto = listaProjeto;
    }

    public List<Funcionario> getListaFuncionario() {
        if (this.listaFuncionario == null) {
            String hql = "SELECT vo FROM Funcionario vo"
                    + " ORDER BY vo.id DESC";
            this.listaFuncionario = new ControladorFuncionario().listar(hql);
        }
        return listaFuncionario;
    }

    public void setListaFuncionario(List<Funcionario> listaFuncionario) {
        this.listaFuncionario = listaFuncionario;
    }

    public String getTextoBusca() {
        return textoBusca;
    }

    public void setTextoBusca(String textoBusca) {
        this.textoBusca = textoBusca;
    }

    public String getColunaBusca() {
        return colunaBusca;
    }

    public void setColunaBusca(String colunaBusca) {
        this.colunaBusca = colunaBusca;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }
}

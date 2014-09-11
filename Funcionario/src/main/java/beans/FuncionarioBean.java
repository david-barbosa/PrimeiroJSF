/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package beans;

import controladores.ControladorFuncionario;
import entidades.Funcionario;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import static util.Util.resetarFormulario;

/**
 *
 * @author David
 */

@ManagedBean
@ViewScoped
public class FuncionarioBean {
    
    private Funcionario funcionario;
    private List<Funcionario> listaFuncionario;
    private String textoBusca;
    private String colunaBusca;
    
    @PostConstruct
    public void init(){
        this.funcionario = new Funcionario();
        this.colunaBusca = "nome";
    }

    public void salvar(){
        if (new ControladorFuncionario().salvar(this.funcionario)) {
            this.listaFuncionario = null;
            this.funcionario = new Funcionario();
            resetarFormulario("form");
        }
    }
    
    public void cancelar(){
        this.funcionario = new Funcionario();
        resetarFormulario("form");
    }
    
    public void pesquisar(){
        String hql = "SELECT funcionario FROM Funcionario funcionario WHERE CAST (funcionario." + this.colunaBusca + " AS text) LIKE '" + this.textoBusca + "%'";
        this.listaFuncionario = new ControladorFuncionario().listar(hql);
    }
    
    public void pesquisarFuncionario(){
        String hql = "SELECT funcionario FROM Funcionario funcionario WHERE funcionario.projeto.id= '" + this.textoBusca + "' ORDER BY funcionario.nome DESC";
        this.listaFuncionario = new ControladorFuncionario().listar(hql);
    }
    
    public void editar(Funcionario funcionario){
        this.funcionario = funcionario;
        resetarFormulario("form");
    }
    
    public void excluir(Funcionario funcionario){
        new ControladorFuncionario().excluir(funcionario);
        this.listaFuncionario = null;
        this.listaFuncionario = null;
    }
    
    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
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
    
    
}

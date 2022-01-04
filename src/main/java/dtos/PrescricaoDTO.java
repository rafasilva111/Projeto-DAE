package dtos;

import entities.Doutor;
import entities.Prescricao;
import entities.UtilizadorNormal;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;


public class PrescricaoDTO implements Serializable {
    private String id;
    private String doutorId;
    private String doutorName;
    private String utilizadorNormalId;
    private String dataFim;
    private String dataInicio;
    private String descricao;
    private String tipo;
    private boolean enabled;

    public PrescricaoDTO(String id,String doutorName, String doutorId, String utilizadorNormalId, Date dataFim, Date dataInicio, String descricao, Prescricao.TypePrescricoes tipo) {
        this.id = id;
        this.doutorName = doutorName;
        this.doutorId = doutorId;
        this.utilizadorNormalId = utilizadorNormalId;
        this.dataFim = new SimpleDateFormat("dd/MM/yyyy kk:mm").format(dataFim);
        this.dataInicio = new SimpleDateFormat("dd/MM/yyyy kk:mm").format(dataInicio);
        this.descricao = descricao;
        this.tipo = tipo.toString();
        this.enabled = true;
    }

    public PrescricaoDTO() {
    }

    public String getDoutorName() {
        return doutorName;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void setDoutorName(String doutorName) {
        this.doutorName = doutorName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDoutorId() {
        return doutorId;
    }

    public void setDoutorId(String doutorId) {
        this.doutorId = doutorId;
    }

    public String getUtilizadorNormalId() {
        return utilizadorNormalId;
    }

    public void setUtilizadorNormalId(String utilizadorNormalId) {
        this.utilizadorNormalId = utilizadorNormalId;
    }

    public void setDataFim(String dataFim) {
        this.dataFim = dataFim;
    }

    public void setDataInicio(String dataInicio) {
        this.dataInicio = dataInicio;
    }

    public String getDataFim() {
        return dataFim;
    }

    public String getDataInicio() {
        return dataInicio;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getTipo() {
        return tipo;
    }
}

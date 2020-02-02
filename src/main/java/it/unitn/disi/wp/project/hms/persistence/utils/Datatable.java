/*
 * AA 2018-2019
 * Introduction to Web Programming
 * Healthcare Management System
 * UniTN
 */
package it.unitn.disi.wp.project.hms.persistence.utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * The entity that describes a Datatable
 *
 * @author Stefano chirico &lt;stefano dot chirico at unitn dot it&gt;
 */
public class Datatable implements Serializable {

    protected Integer draw;
    protected Long recordsTotal;
    protected Long recordsFiltered;
    protected List<?> data;

    public Datatable(Integer draw, Long recordsTotal, Long recordsFiltered, List<?> data) {
        this.draw = draw;
        this.recordsTotal = recordsTotal;
        this.recordsFiltered = recordsFiltered;
        if (data == null) {
            data = new ArrayList<>();
        }
        this.data = data;
    }

    public Datatable() {
        this(1, 0L, 0L, null);
    }

    public Integer getDraw() {
        return draw;
    }

    public void setDraw(Integer draw) {
        this.draw = draw;
    }

    public Long getRecordsTotal() {
        return recordsTotal;
    }

    public void setRecordsTotal(Long recordsTotal) {
        this.recordsTotal = recordsTotal;
    }

    public Long getRecordsFiltered() {
        return recordsFiltered;
    }

    public void setRecordsFiltered(Long recordsFiltered) {
        this.recordsFiltered = recordsFiltered;
    }

    public List<?> getData() {
        return data;
    }

    public void setData(List<?> data) {
        this.data = data;
    }

}
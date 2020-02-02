/*
 * AA 2018-2019
 * Introduction to Web Programming
 * Healthcare Management System
 * UniTN
 */
package it.unitn.disi.wp.project.hms.persistence.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * The entity that describes a Datatable response entity
 *
 * @author Nolbert Juarez &lt;nolbert dot juarezvera at studenti dot unitn dot it&gt;
 * @since 06.01.2020
 */
public class DatatableResponse extends Datatable {

    private transient Long start;
    private transient Long length;
    private transient Integer orderColumn;
    private transient String orderDir;
    private transient String searchValue;

    public DatatableResponse() {
        super();
        start = 0L;
        length = -1L;
        orderColumn = 0;
        orderDir = "asc";
        searchValue = "";
    }

    public DatatableResponse(HttpServletRequest request, HttpServletResponse response) throws IOException {
        this();
        try {
            draw=Integer.parseInt(request.getParameter("draw"));
            this.start=Long.parseLong(request.getParameter("start"));
            this.length=Long.parseLong(request.getParameter("length"));
            this.orderColumn=Integer.parseInt(request.getParameter("order[0][column]"));
        }
        catch (NumberFormatException | UnsupportedOperationException ex) {
            response.sendError(422); //INVALID INPUT
        }
        orderDir = request.getParameter("order[0][dir]");
        searchValue = request.getParameter("search[value]");
    }

    public void setParams(Long recordsTotal, Long recordsFiltered, List<?> data) {
        this.recordsTotal = recordsTotal;
        this.recordsFiltered = recordsFiltered;
        if (data == null) {
            data = new ArrayList<>();
        }
        this.data = data;
    }

    /**
     * Gets orderDir.
     *
     * @return Value of orderDir.
     */
    public String getOrderDir() {
        return orderDir;
    }

    /**
     * Gets length.
     *
     * @return Value of length.
     */
    public Long getLength() {
        return length;
    }

    /**
     * Sets new searchValue.
     *
     * @param searchValue New value of searchValue.
     */
    public void setSearchValue(String searchValue) {
        this.searchValue = searchValue;
    }

    /**
     * Gets start.
     *
     * @return Value of start.
     */
    public Long getStart() {
        return start;
    }

    /**
     * Gets searchValue.
     *
     * @return Value of searchValue.
     */
    public String getSearchValue() {
        return searchValue;
    }

    /**
     * Sets new length.
     *
     * @param length New value of length.
     */
    public void setLength(Long length) {
        this.length = length;
    }

    /**
     * Gets orderColumn.
     *
     * @return Value of orderColumn.
     */
    public Integer getOrderColumn() {
        return orderColumn;
    }

    /**
     * Sets new orderColumn.
     *
     * @param orderColumn New value of orderColumn.
     */
    public void setOrderColumn(Integer orderColumn) {
        this.orderColumn = orderColumn;
    }

    /**
     * Sets new start.
     *
     * @param start New value of start.
     */
    public void setStart(Long start) {
        this.start = start;
    }

    /**
     * Sets new orderDir.
     *
     * @param orderDir New value of orderDir.
     */
    public void setOrderDir(String orderDir) {
        this.orderDir = orderDir;
    }

}

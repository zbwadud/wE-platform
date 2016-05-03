package com.wentity.moversui.controller;

import com.wentity.moversui.domain.UserHasUserRole;
import com.wentity.moversui.controller.util.JsfUtil;
import com.wentity.moversui.controller.util.PaginationHelper;
import com.wentity.moversui.ejb.UserHasUserRoleFacade;

import java.io.Serializable;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;

@Named("userHasUserRoleController")
@SessionScoped
public class UserHasUserRoleController implements Serializable {

    private UserHasUserRole current;
    private DataModel items = null;
    @EJB
    private com.wentity.moversui.ejb.UserHasUserRoleFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;

    public UserHasUserRoleController() {
    }

    public UserHasUserRole getSelected() {
        if (current == null) {
            current = new UserHasUserRole();
            current.setUserHasUserRolePK(new com.wentity.moversui.domain.UserHasUserRolePK());
            selectedItemIndex = -1;
        }
        return current;
    }

    private UserHasUserRoleFacade getFacade() {
        return ejbFacade;
    }

    public PaginationHelper getPagination() {
        if (pagination == null) {
            pagination = new PaginationHelper(10) {

                @Override
                public int getItemsCount() {
                    return getFacade().count();
                }

                @Override
                public DataModel createPageDataModel() {
                    return new ListDataModel(getFacade().findRange(new int[]{getPageFirstItem(), getPageFirstItem() + getPageSize()}));
                }
            };
        }
        return pagination;
    }

    public String prepareList() {
        recreateModel();
        return "List";
    }

    public String prepareView() {
        current = (UserHasUserRole) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new UserHasUserRole();
        current.setUserHasUserRolePK(new com.wentity.moversui.domain.UserHasUserRolePK());
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            current.getUserHasUserRolePK().setUserID(current.getUsers().getUserID());
            current.getUserHasUserRolePK().setRoleID(current.getUserRole().getRoleID());
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("UserHasUserRoleCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (UserHasUserRole) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            current.getUserHasUserRolePK().setUserID(current.getUsers().getUserID());
            current.getUserHasUserRolePK().setRoleID(current.getUserRole().getRoleID());
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("UserHasUserRoleUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (UserHasUserRole) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        performDestroy();
        recreatePagination();
        recreateModel();
        return "List";
    }

    public String destroyAndView() {
        performDestroy();
        recreateModel();
        updateCurrentItem();
        if (selectedItemIndex >= 0) {
            return "View";
        } else {
            // all items were removed - go back to list
            recreateModel();
            return "List";
        }
    }

    private void performDestroy() {
        try {
            getFacade().remove(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("UserHasUserRoleDeleted"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
    }

    private void updateCurrentItem() {
        int count = getFacade().count();
        if (selectedItemIndex >= count) {
            // selected index cannot be bigger than number of items:
            selectedItemIndex = count - 1;
            // go to previous page if last page disappeared:
            if (pagination.getPageFirstItem() >= count) {
                pagination.previousPage();
            }
        }
        if (selectedItemIndex >= 0) {
            current = getFacade().findRange(new int[]{selectedItemIndex, selectedItemIndex + 1}).get(0);
        }
    }

    public DataModel getItems() {
        if (items == null) {
            items = getPagination().createPageDataModel();
        }
        return items;
    }

    private void recreateModel() {
        items = null;
    }

    private void recreatePagination() {
        pagination = null;
    }

    public String next() {
        getPagination().nextPage();
        recreateModel();
        return "List";
    }

    public String previous() {
        getPagination().previousPage();
        recreateModel();
        return "List";
    }

    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
    }

    public UserHasUserRole getUserHasUserRole(com.wentity.moversui.domain.UserHasUserRolePK id) {
        return ejbFacade.find(id);
    }

    @FacesConverter(forClass = UserHasUserRole.class)
    public static class UserHasUserRoleControllerConverter implements Converter {

        private static final String SEPARATOR = "#";
        private static final String SEPARATOR_ESCAPED = "\\#";

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            UserHasUserRoleController controller = (UserHasUserRoleController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "userHasUserRoleController");
            return controller.getUserHasUserRole(getKey(value));
        }

        com.wentity.moversui.domain.UserHasUserRolePK getKey(String value) {
            com.wentity.moversui.domain.UserHasUserRolePK key;
            String values[] = value.split(SEPARATOR_ESCAPED);
            key = new com.wentity.moversui.domain.UserHasUserRolePK();
            key.setUserID(Integer.parseInt(values[0]));
            key.setRoleID(Integer.parseInt(values[1]));
            return key;
        }

        String getStringKey(com.wentity.moversui.domain.UserHasUserRolePK value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value.getUserID());
            sb.append(SEPARATOR);
            sb.append(value.getRoleID());
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof UserHasUserRole) {
                UserHasUserRole o = (UserHasUserRole) object;
                return getStringKey(o.getUserHasUserRolePK());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + UserHasUserRole.class.getName());
            }
        }

    }

}

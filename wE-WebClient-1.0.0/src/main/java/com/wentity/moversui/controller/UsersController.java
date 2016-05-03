package com.wentity.moversui.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wentity.moversui.client.UserClient;
import com.wentity.moversui.controller.model.User;
import com.wentity.moversui.controller.util.JsfUtil;
import com.wentity.moversui.controller.util.PaginationHelper;
import com.wentity.moversui.services.UsersFacadeREST;
import java.io.IOException;

import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;

@Named("usersController")
@SessionScoped
public class UsersController implements Serializable {

    private User current;
    private UserClient clientFacade;
    private DataModel items = null;
    private ObjectMapper mapper;// = new ObjectMapper();
    private PaginationHelper pagination;
    private int selectedItemIndex;
    private UsersFacadeREST userRest = new UsersFacadeREST();

    public UsersController() {
         this.mapper = new ObjectMapper();
    }

    public User getSelected() {
        if (current == null) {
            current = new User();
            selectedItemIndex = -1;
        }
        return current;
    }
    public ObjectMapper getMapper() {
        return mapper;
    }

    public void setMapper(ObjectMapper mapper) {
        this.mapper = mapper;
    }
    private UserClient getClientFacade() {
        clientFacade = new UserClient();
        return clientFacade;
    }   
    
    public PaginationHelper getPagination() {
        if (pagination == null) {
            pagination = new PaginationHelper(10) {

                @Override
                public int getItemsCount() {
                    return Integer.parseInt(getClientFacade().countREST());//getFacade().count();
                }

                @Override
                public DataModel createPageDataModel() {
                    int total =  getPageFirstItem() + getPageSize();                 
                    //getSelected().setUserCollection(getClientFacade().findRange_JSON(List.class, String.valueOf(getPageFirstItem()), String.valueOf(total)));
                    String listOfUser = getClientFacade().findRange_JSON(String.class, String.valueOf(getPageFirstItem()), String.valueOf(total));
                    getClientFacade().close();
        try {
            getSelected().setUserCollection((List<User>) getMapper().readValue(listOfUser.getBytes(), new TypeReference<List<User>>() {
            }));
            //getResponse().close();
           // System.out.println("Mapper " + getUser().getUserCollection().get(0).getEmail());
        } catch (JsonMappingException e) {
            //Logger.getLogger(MoversClient.class.getName()).log(Level.SEVERE, null, e);
        } catch (IOException ex) {
            //Logger.getLogger(MoversClient.class.getName()).log(Level.SEVERE, null, ex);
        }         return new ListDataModel(current.getUserCollection());
                    //return new ListDataModel(getFacade().findRange(new int[]{getPageFirstItem(), getPageFirstItem() + getPageSize()}));                    
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
        current = (User) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new User();
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            String jsonInString = mapper.writeValueAsString(current);
            userRest.create(jsonInString);
                //getClientFacade().create_JSON(jsonInString);
                //getClientFacade().close();
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("UsersCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (User) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            String jsonInString = mapper.writeValueAsString(current);
            userRest.edit(selectedItemIndex, jsonInString);
            //getClientFacade().edit_JSON(jsonInString, current.getUserID().toString());
            //getClientFacade().close();
            //getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("UsersUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (User) getItems().getRowData();
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
            //String jsonInString = mapper.writeValueAsString(current.getUserID().toString());
            System.out.println("detroy--- "+current.getUserID().toString());
            userRest.remove(current.getUserID().toString());
            //getClientFacade().remove(current.getUserID().toString());
            //getClientFacade().close();
            //getFacade().remove(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("UsersDeleted"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
    }

    private void updateCurrentItem() {
        int count = Integer.parseInt(getClientFacade().countREST());
        getClientFacade().close();//getFacade().count();
        if (selectedItemIndex >= count) {
            // selected index cannot be bigger than number of items:
            selectedItemIndex = count - 1;
            // go to previous page if last page disappeared:
            if (pagination.getPageFirstItem() >= count) {
                pagination.previousPage();
            }
        }
        if (selectedItemIndex >= 0) {
            //current = getFacade().findRange(new int[]{selectedItemIndex, selectedItemIndex + 1}).get(0);
            //current = getFacade().findRange_JSON(responseType, from, to)findRange(new int[]{selectedItemIndex, selectedItemIndex + 1}).get(0);
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
        return JsfUtil.getSelectItems(getClientFacade().findAll_JSON(List.class), false);//ejbFacade.findAll()
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(getClientFacade().findAll_JSON(List.class), true);//ejbFacade.findAll()
    }

    public User getUsers(java.lang.Integer id) {
        
            
        return getClientFacade().find_JSON(current.getClass(), id.toString());//ejbFacade.find(id);
    }

    @FacesConverter(forClass = User.class)
    public static class UsersControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            UsersController controller = (UsersController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "usersController");
            return controller.getUsers(getKey(value));
        }

        java.lang.Integer getKey(String value) {
            java.lang.Integer key;
            key = Integer.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Integer value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof User) {
                User o = (User) object;
                return getStringKey(o.getUserID());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + User.class.getName());
            }
        }

    }

}

/*
 * File: CIServer.java
 * ------------------------------
 * When it is finished, this program will implement a basic
 * ecommerce network management server.  Remember to update this comment!
 */

package edu.cis.Controller;

import acm.program.*;
import edu.cis.Model.CISConstants;
import edu.cis.Model.Request;
import edu.cis.Model.SimpleServerListener;
import edu.cis.Utils.SimpleServer;

import java.util.ArrayList;

public class CIServer extends ConsoleProgram
        implements SimpleServerListener
{
    String createUser;
    String addMenuItem;
    String placeOrder;
    String deleteOrder;
    String getOrder;
    String getUser;
    String getItem;
    String getCart;
    String getBalance;
    String getMenuNames;
    String getMenuDescriptions;
    String getMenuTypes;
    String getMenuPrices;
    String getMenuItemIds;
    String getAdminId;
    String getUserName;
    String checkUser;
    String getType;
    String deleteMenuItem;

    /* The internet port to listen to requests on */
    private static final int PORT = 8000;

    /* The server object. All you need to do is start it */
    private SimpleServer server = new SimpleServer(this, PORT);
    ArrayList<CISUser> users = new ArrayList<CISUser>();
    edu.cis.Controller.Menu menu = new edu.cis.Controller.Menu("welcome");

    /**
     * Starts the server running so that when a program sends
     * a request to this server, the method requestMade is
     * called.
     */
    public void run()
    {
        println("Starting server on port " + PORT);
        server.start();
        users = new ArrayList<>();
    }

    /**
     * When a request is sent to this server, this method is
     * called. It must return a String.
     *
     * @param request a Request object built by SimpleServer from an
     *                incoming network request by the client
     */
    public String requestMade(Request request)
    {
        String cmd = request.getCommand();
        println(request.toString());

        if(request.getCommand().equals(CISConstants.CREATE_USER)){
            createUser = createUser(request);
            return createUser;
        }
        if(request.getCommand().equals(CISConstants.ADD_MENU_ITEM)){
            addMenuItem = addMenuItem(request);
            return addMenuItem;
        }
        if(request.getCommand().equals(CISConstants.PLACE_ORDER)){
            placeOrder = placeOrder(request);
            return placeOrder;
        }
        if(request.getCommand().equals(CISConstants.DELETE_ORDER)){
            deleteOrder = deleteOrder(request);
            return deleteOrder;
        }
        if(request.getCommand().equals(CISConstants.GET_ORDER)){
            getOrder = getOrder(request);
            return getOrder;
        }
        if(request.getCommand().equals(CISConstants.GET_ITEM)){
            getItem = getItem(request);
            return getItem(request);
        }
        if(request.getCommand().equals(CISConstants.GET_USER)){
            getUser = getUser(request);
            return getUser;
        }
        if(request.getCommand().equals(CISConstants.GET_CART)){
            getCart = getCart(request);
            return getCart;
        }
        if(request.getCommand().equals(CISConstants.GET_BALANCE)){
            getBalance = getBalance(request);
            return getBalance;
        }
        if(request.getCommand().equals(CISConstants.GET_MENU_NAMES)){
            getMenuNames = getMenuNames(request);
            return getMenuNames;
        }
        if(request.getCommand().equals(CISConstants.GET_MENU_DESCRIPTION)){
            getMenuDescriptions = getMenuDescriptions(request);
            return getMenuDescriptions;
        }
        if(request.getCommand().equals(CISConstants.GET_MENU_TYPES)){
            getMenuTypes = getMenuTypes(request);
            return getMenuTypes;
        }
        if(request.getCommand().equals(CISConstants.GET_MENU_PRICES)){
            getMenuPrices = getMenuPrices(request);
            return getMenuPrices;
        }
        if(request.getCommand().equals(CISConstants.GET_MENU_ITEM_IDS)){
            getMenuItemIds = getMenuItemIds(request);
            return getMenuItemIds;
        }
        if(request.getCommand().equals(CISConstants.GET_ADMIN_ID)){
            getAdminId = getAdminId(request);
            return getAdminId;
        }
        if(request.getCommand().equals(CISConstants.GET_USER_NAME)){
            getUserName = getUserName(request);
            return getUserName;
        }
        if(request.getCommand().equals(CISConstants.CHECK_USER)){
            checkUser = checkUser(request);
            return checkUser;
        }
        if(request.getCommand().equals(CISConstants.GET_TYPE)){
            getType = getType(request);
            return getType;
        }
        if(request.getCommand().equals(CISConstants.DELETE_MENU_ITEM)){
            deleteMenuItem = deleteMenuItem(request);
            return deleteMenuItem;
        }
        if (request.getCommand().equals(CISConstants.PING))
        {
            final String PING_MSG = "Hello, internet";

            //println is used instead of System.out.println to print to the server GUI
            println("   => " + PING_MSG);
            return PING_MSG;
        }
        return "Error: Unknown command " + cmd + ".";
    }
    public String createUser(Request req) {
        String userID = req.getParam(CISConstants.USER_ID_PARAM);
        String name = req.getParam(CISConstants.USER_NAME_PARAM);
        String yearLevel = req.getParam(CISConstants.YEAR_LEVEL_PARAM);
        if(userID == null||name == null||yearLevel == null) {
            return CISConstants.PARAM_MISSING_ERR;
        }
        for(CISUser user : users){
            if(user.getUserID().equals(userID)){
                return CISConstants.DUP_USER_ERR;
            }
        }
        CISUser user = new CISUser(userID,name,yearLevel);
        users.add(user);
        return CISConstants.SUCCESS;
    }
    public String addMenuItem(Request req){
        String itemID = req.getParam(CISConstants.ITEM_ID_PARAM);
        String name = req.getParam(CISConstants.ITEM_NAME_PARAM);
        String description = req.getParam(CISConstants.DESC_PARAM);
        String type = req.getParam(CISConstants.ITEM_TYPE_PARAM);
        double price = Double.valueOf(req.getParam(CISConstants.PRICE_PARAM));
        if(itemID == null||name == null||type == null||description == null||req.getParam(CISConstants.PRICE_PARAM) == null){
            return CISConstants.PARAM_MISSING_ERR;
        }
        for(MenuItem item : menu.getEatriumItems()){
            if(item.getId().equals(itemID)){
                return CISConstants.DUP_ITEM_ERR;
            }
        }
        MenuItem newItem = new MenuItem(name,description,price,type,itemID);
        menu.addEatriumItems(newItem);
        return CISConstants.SUCCESS;
    }

    public String placeOrder(Request req){
        String orderId = req.getParam(CISConstants.ORDER_ID_PARAM);
        String itemId = req.getParam(CISConstants.ITEM_ID_PARAM);
        String userId = req.getParam(CISConstants.USER_ID_PARAM);
        String orderType = req.getParam(CISConstants.ORDER_TYPE_PARAM);
        if(orderId == null||itemId == null||userId == null||orderType == null){
            return CISConstants.PARAM_MISSING_ERR;
        }
        if(menu.getEatriumItems().isEmpty()){
            return CISConstants.EMPTY_MENU_ERR;
        }
        boolean userExists = false;
        boolean orderIdExists = false;
        boolean orderIdUsedByOther = false;
        boolean soldOut = false;
        boolean itemExists = false;
        CISUser preTestUser = new CISUser();
        MenuItem preTestItem = new MenuItem();
        for(CISUser user:users){
            if(user.getUserID().equals(userId)){
                preTestUser = user;
                userExists = true;
                break;
            }
        }
        if(!userExists){
            return CISConstants.USER_INVALID_ERR;
        }
        for(Order order : preTestUser.getOrders()){
            if(order.getOrderID().equals(orderId)){
                orderIdExists = true;
                break;
            }
        }
        if(orderIdExists){
            return CISConstants.DUP_ORDER_ERR;
        }
        for(CISUser user : users){
            for(Order order : user.getOrders()){
                if(order.getOrderID().equals(orderId)){
                    orderIdUsedByOther = true;
                    break;
                }
            }
        }
        if(orderIdUsedByOther){
            return CISConstants.DUP_USER_ERR;
        }
        for(MenuItem item : menu.getEatriumItems()){
            if(item.getId().equals(itemId)){
                preTestItem = item;
                itemExists = true;
                if(item.getAmountAvailable() == 0){
                    soldOut = true;
                }
                break;
            }
        }
        if(!itemExists){
            return CISConstants.INVALID_MENU_ITEM_ERR;
        }
        if(soldOut){
            return CISConstants.SOLD_OUT_ERR;
        }
        if(preTestUser.getMoney() < preTestItem.getPrice()){
            return CISConstants.USER_BROKE_ERR;
        }
        Order order = new Order(itemId, orderType, orderId);
        preTestUser.setMoney(preTestUser.getMoney()-preTestItem.getPrice());
        preTestUser.addOrder(order);
        preTestItem.setAmountAvailable(preTestItem.getAmountAvailable()-1);
        return CISConstants.SUCCESS;
    }

    public String deleteOrder(Request req){
        String userId = req.getParam(CISConstants.USER_ID_PARAM);
        String orderId = req.getParam(CISConstants.ORDER_ID_PARAM);
        if(userId == null||orderId == null){
            return CISConstants.PARAM_MISSING_ERR;
        }
        for(CISUser user : users){
            if(user.getUserID().equals(userId)){
                for(Order order : user.getOrders()){
                    if(order.getOrderID().equals(orderId)){
                        user.deleteOrder(order);
                        return CISConstants.SUCCESS;
                    }
                }
            }
        }
        return CISConstants.ORDER_INVALID_ERR;
    }

    public String getOrder(Request req){
        String userId = req.getParam(CISConstants.USER_ID_PARAM);
        String orderId = req.getParam(CISConstants.ORDER_ID_PARAM);
        if(userId == null||orderId == null){
            return CISConstants.PARAM_MISSING_ERR;
        }
        for(CISUser user : users){
            if(user.getUserID().equals(userId)){
                for(Order order:user.getOrders()){
                    if(order.getOrderID().equals(orderId)){
                        return "Order{itemID='"+order.getItemID()+"', type='"+order.getType()+"', orderID='"+order.getOrderID()+"'}";
                    }
                }
            }
        }
        return CISConstants.ORDER_INVALID_ERR;
    }

    public String getUser(Request req){
        String userID = req.getParam(CISConstants.USER_ID_PARAM);
        for(CISUser user : users){
            if(user.getUserID().equals(userID)){
                return "CISUser{userID='"+user.getUserID()+"', name='"+user.getName()+"', yearLevel='"+user.getYearLevel()+"', "+getCart(req)+"money="+String.valueOf(user.getMoney())+"}";
            }
        }
        return CISConstants.USER_INVALID_ERR;
    }

    public String getItem(Request req){
        String itemId = req.getParam(CISConstants.ITEM_ID_PARAM);
        for(MenuItem item : menu.getEatriumItems()){
            if(item.getId().equals(itemId)){
                return "MenuItem{name='"+ item.getName()+ "', description='" + item.getDescription()+ "', price="+String.valueOf(item.getPrice())+", id='" +item.getId()+ "', amountAvailable="+String.valueOf(item.getAmountAvailable())+", type='"+item.getType()+"'}";
            }
        }
        return CISConstants.INVALID_MENU_ITEM_ERR;
    }

    public String getCart(Request req){
        String userId = req.getParam(CISConstants.USER_ID_PARAM);
        for(CISUser user : users){
            if(user.getUserID().equals(userId)){
                String cart = "orders= ";
                for(Order order : user.getOrders()){
                    Request request = new Request(CISConstants.GET_ORDER);
                    request.addParam(CISConstants.USER_ID_PARAM, userId);
                    request.addParam(CISConstants.ORDER_ID_PARAM, order.getOrderID());
                    cart += getOrder(request)+", ";
                }
                return cart;
            }
        }
        return CISConstants.USER_INVALID_ERR;
    }
    public String getBalance(Request req) {
        String userID = req.getParam(CISConstants.USER_ID_PARAM);
        String remainingBalance = "";
        if(userID == null) {
            return CISConstants.PARAM_MISSING_ERR;
        }
        for(CISUser user : users){
            if(user.getUserID().equals(userID)){
                remainingBalance += String.valueOf(user.getMoney());
                break;
            }
        }
        return remainingBalance;
    }
    public String getMenuNames(Request req){
        String menuNames = "";
        for(MenuItem item: menu.getEatriumItems()){
            menuNames += (item.getName() + "#");
        }
        return menuNames;
    }
    public String getMenuDescriptions(Request req){
        String menuDescriptions = "";
        for(MenuItem item: menu.getEatriumItems()){
            menuDescriptions += (item.getDescription() + "#");
        }
        return menuDescriptions;
    }
    public String getMenuTypes(Request req){
        String menuTypes = "";
        for(MenuItem item: menu.getEatriumItems()){
            menuTypes += (item.getType() + "#");
        }
        return menuTypes;
    }
    public String getMenuPrices(Request req){
        String menuPrices = "";
        for(MenuItem item: menu.getEatriumItems()){
            menuPrices += (item.getPrice() + "#");
        }
        return menuPrices;
    }
    public String getMenuItemIds(Request req){
        String menuItemIds = "";
        for(MenuItem item: menu.getEatriumItems()){
            menuItemIds += (item.getId() + "#");
        }
        return menuItemIds;
    }
    public String getAdminId(Request req){
        String adminId = menu.getAdminID();
        return adminId;
    }
    public String getUserName(Request req){
        String userID = req.getParam(CISConstants.USER_ID_PARAM);
        String userName = "";
        for(CISUser user : users){
            if(user.getUserID().equals(userID)){
                userName = user.getName();
                break;
            }
        }
        return userName;
    }
    public String checkUser(Request req){
        String userID = req.getParam(CISConstants.USER_ID_PARAM);
        for(CISUser user : users){
            if(user.getUserID().equals(userID)){
                return CISConstants.SUCCESS;
            }
        }
        return CISConstants.USER_INVALID_ERR;
    }
    public String getType(Request req){
        String itemId = req.getParam(CISConstants.ITEM_ID_PARAM);
        for(MenuItem item : menu.getEatriumItems()){
            if(item.getId().equals(itemId)){
                return item.getType();
            }
        }
        return null;
    }
    public String deleteMenuItem(Request req){
        String itemId = req.getParam(CISConstants.ITEM_ID_PARAM);
        for(MenuItem item : menu.getEatriumItems()){
            if(item.getId().equals(itemId)){
                menu.deleteEatriumItem(item);
                return CISConstants.SUCCESS;
            }
        }
        return CISConstants.INVALID_MENU_ITEM_ERR;
    }

    public static void main(String[] args)
    {
        CIServer f = new CIServer();
        f.start(args);
        f.run();
    }
}
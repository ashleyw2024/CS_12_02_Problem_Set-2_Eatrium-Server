package edu.cis.Model;

public class CISConstants
{
    //return strings
    public static final String DUP_ORDER_ERR = "Error: meal/drink already ordered by this user.";
    public static final String ORDER_INVALID_ERR = "Error: order doesn't exist.";
    public static final String INVALID_MENU_ITEM_ERR = "Error: there is no menu item for that name.";
    public static final String DUP_ITEM_ERR = "Error: menu item already exists in menu.";
    public static final String USER_INVALID_ERR = "Error: there is no user for that ID.";
    public static final String DUP_USER_ERR = "Error: a user with that ID already exists.";
    public static final String SOLD_OUT_ERR = "Error: menu item has been sold out.";
    public static final String USER_BROKE_ERR = "Error: the user doesn't have enough money for this item.";
    public static final String EMPTY_MENU_ERR = "Error: there are no items available in the menu server";
    public static final String PARAM_MISSING_ERR = "Error: need more information to complete request. Are you forgetting any parameters?";
    public static final String DUP_TOP_UP_CODE = "Error: a card with that top up code already exists.";
    public static final String TRANSACTION_FAIL = "Error: transaction failed.";
    public static final String SUCCESS = "success";
    public static final String TRUE_RET = "true";
    public static final String FALSE_RET = "false";

    //Parameters for requests
    public static final String ITEM_NAME_PARAM = "menuItemName";
    public static final String ITEM_TYPE_PARAM = "menuItemType";
    public static final String ITEM_ID_PARAM = "menuItemID";
    public static final String USER_NAME_PARAM = "userName";
    public static final String YEAR_LEVEL_PARAM = "yearLevel";
    public static final String ORDER_ID_PARAM = "orderID";
    public static final String ORDER_TYPE_PARAM = "orderType";
    public static final String PRICE_PARAM = "price";
    public static final String USER_ID_PARAM = "userID";
    public static final String DESC_PARAM = "description";
    public static final String TOP_UP_CODE = "topUpCode";
    public static final String TOP_UP_AMOUNT = "topUpAmount";

    //Commands
    //create user
    //place order
    //delete order
    //get user cart
    //add menu item
    //delete menu item
    //get full menu
    public static final String PING = "ping";
    public static final String CREATE_USER = "createUser";
    public static final String PLACE_ORDER = "placeOrder";
    public static final String DELETE_ORDER = "deleteOrder";
    public static final String GET_CART = "getCart";
    public static final String GET_ORDER = "getOrder";
    public static final String GET_ITEM = "getItem";
    public static final String ADD_MENU_ITEM = "addMenuItem";
    public static final String DELETE_MENU_ITEM = "deleteMenuItem";
    public static final String GET_MENU = "getMenu";
    public static final String GET_USER = "getUser";
    public static final String GET_BALANCE = "getBalance";
    public static final String GET_MENU_NAMES = "getMenuNames";
    public static final String GET_MENU_DESCRIPTION = "getMenuDescriptions";
    public static final String GET_MENU_TYPES = "getMenuTypes";
    public static final String GET_MENU_PRICES = "getMenuPrices";
    public static final String GET_MENU_ITEM_IDS = "getMenuItemIds";
    public static final String GET_ADMIN_ID = "getAdminId";
    public static final String GET_USER_NAME = "getName";
    public static final String CHECK_USER = "checkUser";
    public static final String GET_TYPE = "getType";
    public static final String ADD_TOP_UP_CARD = "addTopUpCard";
    public static final String TOP_UP = "topUp";

    public static final String GET_TOP_UP_CARDS = "getTopUpCards";

    //Errors
    public static final String PORT_UNAVAIL = "is not available, likely because \nit's already being used by another " +
            "Java program running. \nClose all your server windows and try again.";
}

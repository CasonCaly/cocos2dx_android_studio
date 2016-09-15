#import "SDKCenter.h"
#include "XAccount.h"

@interface AccountSDKCallback: NSObject<AccountSDKDelegate>
{
    
}

@property (nonatomic, assign) Account* account;

-(id)initWithAccount:(Account*)account;

-(void)didLoginFinished:(NSString*)error;

-(void)didLogoutFinished:(LogoutFrom)from;

@end

@implementation AccountSDKCallback

-(id)initWithAccount:(Account*)account{
    if(self = [super init]) {
        self.account = account;
    }
    return self;
}

-(void)didLoginFinished:(NSString*)error{
    if(nullptr == self.account)
        return;
    AccountDelegate* _delegate = self.account->getDelegate();
    if(nullptr == _delegate)
        return;
    const char* szError = [error UTF8String];
    if(szError && (szError[0] == 0))
        szError = nullptr;
    _delegate->didLoginFinished(szError);
    
}

-(void)didLogoutFinished:(LogoutFrom)from{
    if(nullptr == self.account)
        return;
    AccountDelegate* _delegate = self.account->getDelegate();
    if(nullptr == _delegate)
        return;
    _delegate->didLogoutFinished((AccountLogoutFrom)from);
}

@end

void AccountFriend::setId(const char* id)
{
	if(nullptr != id)
		m_id = id;
}

const char* AccountFriend::getId()
{
	return m_id.c_str();
}


void AccountFriend::setProfileImage(const char* profileImage)
{
	if(nullptr != profileImage)
		m_profileImage = profileImage;
}

const char* AccountFriend::getProfileIamge()
{
	return m_profileImage.c_str();
}

void AccountFriend::setName(const char* name)
{
	if(nullptr != name)
		m_name = name;
}

const char* AccountFriend::getName()
{
	return m_name.c_str();
}

void AccountFriend::setGender(const char* gender)
{
	if (nullptr != gender)
		m_gender = gender;
}

const char* AccountFriend::getGender()
{
	return m_gender.c_str();
}

void AccountFriend::setFirstName(const char* firstName)
{
	if(nullptr != firstName)
		m_firstName = firstName;
}

const char* AccountFriend::getFirstName()
{
	return m_firstName.c_str();
}

void AccountFriend::setMiddleName(const char* middleName)
{
	if(nullptr != middleName)
		m_middleName = middleName;
}

const char* AccountFriend::getMiddleName()
{
	return m_middleName.c_str();
}

void AccountFriend::setLastName(const char* lastName)
{
	if(nullptr == lastName)
		m_lastName = lastName;
}

const char* AccountFriend::getLastName()
{
	return m_lastName.c_str();
}


static std::string s_empty;

const char* safeNString2String(NSString* ns){
    return ns == nil ? s_empty.c_str() : [ns UTF8String];
}

Account::Account()
{
    m_delegate = nullptr;
    AccountSDK* accountSDK = [SDKCenter account];
    accountSDK.delegate = [[AccountSDKCallback alloc] initWithAccount:this];
}

Account::~Account()
{
}

void Account::prepare()
{
    AccountSDK* accountSDK = [SDKCenter account];
    [accountSDK prepareSDK];
}

bool Account::isDefault()
{
	AccountSDK* accountSDK = [SDKCenter account];
	return accountSDK.isDefault;
}

bool Account::hasUserCenter()
{
    AccountSDK* accountSDK = [SDKCenter account];
    return accountSDK.hasUserCenter;
}

const char* Account::getName()
{
    AccountSDK* accountSDK = [SDKCenter account];
    return safeNString2String(accountSDK.name);
}

const char* Account::getId()
{
    AccountSDK* accountSDK = [SDKCenter account];
    return safeNString2String(accountSDK.accountId);
}

const char* Account::getSessionId()
{
    AccountSDK* accountSDK = [SDKCenter account];
    return safeNString2String(accountSDK.sessionId);
}

const char* Account::getGender()
{
    AccountSDK* accountSDK = [SDKCenter account];
    return safeNString2String(accountSDK.gender);
}

const char* Account::getFirstName()
{
    AccountSDK* accountSDK = [SDKCenter account];
    return safeNString2String(accountSDK.firstName);
}

const char* Account::getLastName()
{
    AccountSDK* accountSDK = [SDKCenter account];
    return safeNString2String(accountSDK.lastName);
}

const char* Account::getLocale()
{
    AccountSDK* accountSDK = [SDKCenter account];
    return safeNString2String(accountSDK.locale);
}

const char* Account::getEmail()
{
    AccountSDK* accountSDK = [SDKCenter account];
    return safeNString2String(accountSDK.email);
}

const char* Account::getProfileImage()
{
    AccountSDK* accountSDK = [SDKCenter account];
    return safeNString2String(accountSDK.profileImage);
}

const char* Account::getChannelId()
{
    AccountSDK* accountSDK = [SDKCenter account];
    return safeNString2String(accountSDK.channelId);
}

const char* Account::getChannelName()
{
    AccountSDK* accountSDK = [SDKCenter account];
    return safeNString2String(accountSDK.channelName);
}

const char* Account::getAppId()
{
    AccountSDK* accountSDK = [SDKCenter account];
    return safeNString2String(accountSDK.appid);
}

const char* Account::getAppKey()
{
    AccountSDK* accountSDK = [SDKCenter account];
    return safeNString2String(accountSDK.appkey);
}

void Account::clean()
{
}

void Account::login(){
    AccountSDK* accountSDK = [SDKCenter account];
    [accountSDK login];
}

void Account::logout(){
    AccountSDK* accountSDK = [SDKCenter account];
    [accountSDK logout];
}

void Account::swtichAccount(){
    AccountSDK* accountSDK = [SDKCenter account];
    [accountSDK swtichAccount];
}

void Account::gotoUserCetner(){
    AccountSDK* accountSDK = [SDKCenter account];
    [accountSDK gotoUserCetner];
}

void Account::gotoBBS(){
    AccountSDK* accountSDK = [SDKCenter account];
    [accountSDK gotoBBS];
}

void Account::gotoEnterAPPCetner(){
    AccountSDK* accountSDK = [SDKCenter account];
    [accountSDK gotoEnterAPPCetner];
}

void Account::showToolbar(bool visible){
    AccountSDK* accountSDK = [SDKCenter account];
    [accountSDK showToolbar:visible];
}

void Account::showToolbar(bool visible, AccountToolBarPlace place)
{
    AccountSDKToolBarPlace sdkPlace = SDKToolBarAtMiddleLeft;
    switch(place){
        case  ToolBarAtTopLeft: /* 左上 */
            sdkPlace = SDKToolBarAtTopLeft;
            break;
        case  ToolBarAtTopRight:      /* 右上 */
            sdkPlace = SDKToolBarAtTopRight;
            break;
        case ToolBarAtMiddleLeft:    /* 左中 */
            sdkPlace = SDKToolBarAtMiddleLeft;
            break;
        case ToolBarAtMiddleRight:  /* 右中 */
            sdkPlace = SDKToolBarAtMiddleRight;
            break;
        case ToolBarAtBottomLeft:    /* 左下 */
            sdkPlace = SDKToolBarAtBottomLeft;
            break;
        case ToolBarAtBottomRight:   /* 右下 */
            sdkPlace = SDKToolBarAtBottomRight;
            break;
        default:
            sdkPlace = SDKToolBarAtMiddleLeft;
            break;
    }
    AccountSDK* accountSDK = [SDKCenter account];
    [accountSDK showToolbar:visible withToolBarPlace:sdkPlace];
}

const char* Account::getOtherInfo(const char* key){
    AccountSDK* accountSDK = [SDKCenter account];
    return [accountSDK getOtherInfo:key];
}

void Account::setOtherInfo(const char* key, const char* value){
    AccountSDK* accountSDK = [SDKCenter account];
    return [accountSDK setOtherInfo:key value:value];
}

void Account::callFuntionBegin(){
    AccountSDK* accountSDK = [SDKCenter account];
    return [accountSDK callFuntionBegin];
}

void Account::addFunctionParam(const char* key, const char* value){
    AccountSDK* accountSDK = [SDKCenter account];
    return [accountSDK addFunctionParam:key value:value];
}

void Account::callFunction(const char* name){
    AccountSDK* accountSDK = [SDKCenter account];
    return [accountSDK callFunction: [NSString stringWithUTF8String:name]];
}

void Account::callFunctionEnd(){
    AccountSDK* accountSDK = [SDKCenter account];
    [accountSDK callFunctionEnd];
}

void Account::setSyncParam(const char* key, const char* value){
	if(key && value){
		AccountSDK* accountSDK = [SDKCenter account];
		[accountSDK setSyncParam:[NSString stringWithUTF8String:key] value:[NSString stringWithUTF8String:value]];
	}
}

const char* Account::getSyncParam(const char* key){
	if(nullptr == key)
		return s_empty.c_str();
	
	AccountSDK* accountSDK = [SDKCenter account];
	NSString* value = [accountSDK getSyncParam:[NSString stringWithUTF8String:key]];
	return safeNString2String(value);
}

std::map<std::string, std::string> Account::getAllSyncParam(){
	std::map<std::string, std::string> allSync;
	AccountSDK* accountSDK = [SDKCenter account];
	NSDictionary* dic = [accountSDK getAllSyncParam];
	NSArray* allKey = [dic allKeys];
	for(NSInteger i = 0; i < [allKey count]; i++)
	{
		NSString* key = (NSString*)[allKey objectAtIndex:i];
		NSString* value = (NSString*)[dic objectForKey:key];
		allSync.insert(make_pair(safeNString2String(key), safeNString2String(value)));
	}
	return allSync;
}

void Account::cleanSyncParam(){
	AccountSDK* accountSDK = [SDKCenter account];
	[accountSDK cleanSyncParam];
}

void Account::setDefaultAccountSDKByClassName(const char* className){
    [[SDKCenter account] clear];
    AccountSDK* accountSDK = [SDKCenter account];
    accountSDK.delegate = [[AccountSDKCallback alloc] initWithAccount:this];
}

void Account::setDelegate(AccountDelegate* _delegate){
    AccountDelegate* oldDelegate = m_delegate;
    m_delegate = _delegate;
    if (m_delegate){
        Ref* obj =	dynamic_cast<Ref*>(m_delegate);
        if (obj)
        obj->retain();
    }
    
    if (oldDelegate){
        Ref* obj = dynamic_cast<Ref*>(m_delegate);
        if (obj)
        obj->release();
        else
        delete oldDelegate;
    }
}

AccountDelegate* Account::getDelegate(){
    return m_delegate;
}

void Account::addFriend(AccountFriend* accountFriend){
	m_friendList.addObject(accountFriend);
}

int Account::getFriendCount(){
	return (int)m_friendList.count();
}

AccountFriend* Account::getFriend(int index)
{
	if(index >= m_friendList.count())
		return nullptr;
	Ref* objFriend = m_friendList.getObjectAtIndex(index);
	return dynamic_cast<AccountFriend*>(objFriend);
}

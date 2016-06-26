#include "XAccount.h"
#include "json/XJson.h"
#include <map>

static std::string s_empty;
static std::map<std::string, std::string> s_otherInfo;

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

///////////////////////////////////////////////////////////////////////	
Account::Account()
{
    m_delegate = nullptr;
	m_friendList.init();
}

Account::~Account(){
}

void Account::prepare()
{

}

bool Account::isDefault()
{
	return true;
}

bool Account::hasUserCenter()
{
	return false;
}

const char* Account::getName()
{
	return s_empty.c_str();
}

const char* Account::getId()
{
	return s_empty.c_str();
}

const char* Account::getSessionId()
{
	return s_empty.c_str();
}

const char* Account::getGender()
{
	return s_empty.c_str();
}

const char* Account::getFirstName()
{
	return s_empty.c_str();
}

const char* Account::getLastName()
{
	return s_empty.c_str();
}

const char* Account::getLocale()
{
	return s_empty.c_str();
}

const char* Account::getEmail()
{
	return s_empty.c_str();
}

const char* Account::getProfileImage()
{
	return s_empty.c_str();
}

const char* Account::getChannelId()
{
	return s_empty.c_str();
}

const char* Account::getChannelName()
{
	return s_empty.c_str();
}
	
const char* Account::getAppId()
{
	return s_empty.c_str();
}

const char* Account::getAppKey()
{
	return s_empty.c_str();
}

void Account::clean()
{
}

void Account::login()
{
	if (m_delegate)
		m_delegate->didLoginFinished(nullptr);
}

void Account::logout()
{
	if (m_delegate)
		m_delegate->didLogoutFinished(LogoutFromManual);
}

void Account::swtichAccount()
{

}

void Account::gotoUserCetner()
{

}

void Account::gotoBBS()
{

}

void Account::gotoEnterAPPCetner()
{

}

void Account::showToolbar(bool visible)
{
}

void Account::showToolbar(bool visible, AccountToolBarPlace place)
{
  
}

const char* Account::getOtherInfo(const char* key)
{
	if (nullptr == key)
		return s_empty.c_str();
	auto target = s_otherInfo.find(key);
	if (target != s_otherInfo.end())
		return target->second.c_str();
	else
		return s_empty.c_str();
}

void Account::setOtherInfo(const char* key, const char* value)
{
	if (key && value)
		s_otherInfo.insert(std::make_pair(key, value));
}

void Account::callFuntionBegin()
{

}

void Account::addFunctionParam(const char* key, const char* value)
{

}

void Account::callFunction(const char* name)
{

}

void Account::callFunctionEnd()
{
}

void Account::setDefaultAccountSDKByClassName(const char* className)
{

}

void Account::setSyncParam(const char* key, const char* value)
{
}

const char* Account::getSyncParam(const char* key)
{
	return s_empty.c_str();
}

std::map<std::string, std::string> Account::getAllSyncParam()
{
	std::map<std::string, std::string> allSync;
	return allSync;
}

void Account::cleanSyncParam()
{
}

void Account::setDelegate(AccountDelegate* _delegate)
{
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

AccountDelegate* Account::getDelegate()
{
    return m_delegate;
}

void Account::addFriend(AccountFriend* accountFriend)
{
	m_friendList.addObject(accountFriend);
}

int Account::getFriendCount()
{
	return (int)m_friendList.count();
}

AccountFriend* Account::getFriend(int index)
{
	if(index >= m_friendList.count())
		return nullptr;
	Ref* objFriend = m_friendList.getObjectAtIndex(index);
	return dynamic_cast<AccountFriend*>(objFriend);
}
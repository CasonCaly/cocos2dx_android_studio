#ifndef SCX_GAME_ANALYSIS_H
#define SCX_GAME_ANALYSIS_H
#include <string>
#include <map>
#include <vector>
using namespace std;
#include "cocos2d.h"
using namespace cocos2d;



class GameAnalysis : public Ref
{
public:

	GameAnalysis();

	~GameAnalysis();

	void prepare();

public:

	void setAccountId(const char* szAccountId);

	void setAccountType(const char* szAccountType);

	void setAccountName(const char* szAccountName);

	void setLevel(int level);

	void setGameServer(const char* szGameServer);

	void setGender(bool isFemal);

	void setAge(int age);
	
	void setGameServerName(const char* szGameServerName);

	void commit();
	
public:

	void traceChargeRequest(const char* orderId, const char* iapId, double currencyAmount, double virtualCurrencyAmount, const char* paymentType);

	void traceChargeSuccess(const char* orderId);

public:

	void traceReward(double virtualCurrencyAmount, const char* reason);

public:

	void traceGamePurchase(const char* item, int itemNumber, double priceInVirtualCurrency);

	void traceGameUse(const char* item, int itemNumber);

public:

	void traceMission(const char* missionId);

	void traceMissionCompleted(const char* missionId);

	void traceMissionFailed(const char* missionId, const char* failedCause);

};


#endif
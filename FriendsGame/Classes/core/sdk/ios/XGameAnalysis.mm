#include "XGameAnalysis.h"

GameAnalysis::GameAnalysis()
{
}

GameAnalysis::~GameAnalysis()
{
}

void GameAnalysis::prepare()
{
}

void GameAnalysis::setAccountId(const char* szAccountId)
{
}

void GameAnalysis::setAccountType(const char* szAccountType)
{
}

void GameAnalysis::setAccountName(const char* szAccountName)
{
}

void GameAnalysis::setLevel(int level)
{
}

void GameAnalysis::setGameServer(const char* szGameServer)
{
}

void GameAnalysis::setGender(bool isFemal)
{
}

void GameAnalysis::setAge(int age)
{
}

void GameAnalysis::setGameServerName(const char* szGameServerName)
{
}

void GameAnalysis::commit()
{
}


void GameAnalysis::traceChargeRequest(const char* orderId, const char* iapId, double currencyAmount, double virtualCurrencyAmount, const char* paymentType)
{
}

void GameAnalysis::traceChargeSuccess(const char* orderId)
{
}

void GameAnalysis::traceReward(double virtualCurrencyAmount, const char* reason)
{
}

void GameAnalysis::traceGamePurchase(const char* item, int itemNumber, double priceInVirtualCurrency)
{
}

void GameAnalysis::traceGameUse(const char* item, int itemNumber)
{
}

void GameAnalysis::traceMission(const char* missionId)
{
}

void GameAnalysis::traceMissionCompleted(const char* missionId)
{
}

void GameAnalysis::traceMissionFailed(const char* missionId, const char* failedCause)
{
}


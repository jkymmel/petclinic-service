import datetime

from pysimplesoap.client import SoapClient

print('Starting up!')

client = SoapClient(wsdl='http://localhost:8080/ws/PetClinicWebService.wsdl', trace=False)

api_key = input('Please enter API key: ')


def format_pet(pet):
    return f'PET:\n  + ID: {pet["id"]}\n  + name: {pet["name"]}\n  + owner: {pet["owner"]}\n' \
           f'  + veterinarian: {format_vet(pet.get("veterinarian", None))}'


def format_vet(vet):
    if vet is not None:
        return f'\n    + ID: {vet["id"]}\n    + name: {vet["name"]}\n    + email: {vet["email"]}\n' \
               f'    + phone number: {vet["phoneNumber"]}\n    + personal code: {vet["personalCode"]}'
    else:
        return 'None'


while True:
    operation = input(
        'Available operations:\n0: List all pets\n1: Add new Pet\n9: Exit\nPlease enter the number of operation: ')
    if operation == '0':
        pets = client.GetAllPets(
            apiKey=api_key
        )
        print('\nPETS:\n')
        for pet in pets['pet']:
            print(format_pet(pet))
        print('\n\n')
    elif operation == '1':
        name = input('Enter name: ')
        owner = input('Enter owner: ')
        birthday = input('Enter birthday (yyyy-mm-dd): ')
        pet = client.SavePet(
            pet={
                'name': name,
                'owner': owner,
                'birthday': datetime.datetime.strptime(birthday, "%Y-%m-%d").date()
            }
        )
        print('Saved!')
        print(pet['pet'])
        print('\n\n')
    elif operation == '9':
        exit(0)
    else:
        print('Unknown operation!\n\n')

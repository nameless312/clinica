import { AlertStatus, createStandaloneToast } from "@chakra-ui/react"

const { toast } = createStandaloneToast()

const notification = (title: string, description: string, status: AlertStatus) => {
    toast({
        title,
        description,
        status,
        isClosable: true,
        duration: 4000
    })
}

export const successNotification = (title: string, description: string) => {
    notification(
        title,
        description,
        "success"
    )
}

export const errorNotification = (title: string, description: string) => {
    notification(
        title,
        description,
        "error"
    )
}
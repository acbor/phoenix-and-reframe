defmodule App.RoomChannel do
  use App.Web, :channel

  def join("room:lobby", _payload, socket), do: {:ok, socket}

  def handle_in("set", %{"value" => _value} = payload, socket) do
    broadcast! socket, "set", payload
    {:noreply, socket}
  end
end
